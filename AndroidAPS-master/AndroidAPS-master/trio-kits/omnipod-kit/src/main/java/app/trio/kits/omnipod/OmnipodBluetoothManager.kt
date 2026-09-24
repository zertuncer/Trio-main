package app.trio.kits.omnipod

import android.util.Log
import com.juul.kable.Filter
import com.juul.kable.Peripheral
import com.juul.kable.Scanner
import com.juul.kable.State
import com.juul.kable.peripheral
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID

class OmnipodBluetoothManager {
    private val TAG = "OmnipodBLEManager"
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    
    // Custom Omnipod DASH / RileyLink UUIDs
    private val OMNIPOD_SERVICE_UUID = UUID.fromString("00004024-0000-1000-8000-00805f9b34fb")
    
    private val scanner = Scanner {
        filters = listOf(Filter.Service(OMNIPOD_SERVICE_UUID))
    }
    
    private var activePeripheral: Peripheral? = null
    
    private val _connectionState = MutableStateFlow<String>("Disconnected")
    val connectionState: StateFlow<String> = _connectionState
    
    fun scanAndConnect(podId: String) {
        _connectionState.value = "Scanning for Omnipod ($podId)..."
        Log.d(TAG, "Starting BLE scan for Omnipod (ID: $podId)")
        
        scope.launch {
            try {
                val advertisement = scanner.advertisements
                    .catch { e -> Log.e(TAG, "Scan error", e) }
                    .firstOrNull { adv -> 
                        adv.name?.contains("Omnipod", ignoreCase = true) == true || 
                        adv.name?.contains("RileyLink", ignoreCase = true) == true
                    }
                    
                if (advertisement != null) {
                    Log.d(TAG, "Found Omnipod/RL: ${advertisement.name}, Mac: ${advertisement.address}")
                    _connectionState.value = "Found ${advertisement.name}. Connecting..."
                    connectToPeripheral(advertisement)
                } else {
                    _connectionState.value = "Not Found"
                    Log.d(TAG, "Omnipod not found in scan.")
                }
            } catch (e: Exception) {
                _connectionState.value = "Error: ${e.message}"
                Log.e(TAG, "Failed to scan/connect", e)
            }
        }
    }
    
    private suspend fun connectToPeripheral(advertisement: com.juul.kable.Advertisement) {
        val peripheral = scope.peripheral(advertisement)
        activePeripheral = peripheral
        
        peripheral.state.onEach { state ->
            Log.d(TAG, "Omnipod state changed: $state")
            when (state) {
                is State.Connected -> {
                    _connectionState.value = "Connected"
                    // TODO: Implement DASH Sequence / RileyLink Exchange
                }
                is State.Disconnected -> _connectionState.value = "Disconnected"
                is State.Connecting -> _connectionState.value = "Connecting..."
                is State.Disconnecting -> _connectionState.value = "Disconnecting..."
            }
        }.launchIn(scope)
        
        try {
            peripheral.connect()
        } catch (e: Exception) {
            Log.e(TAG, "Omnipod Connection failed", e)
            _connectionState.value = "Connection Failed"
        }
    }
    
    fun disconnect() {
        scope.launch {
            activePeripheral?.disconnect()
            activePeripheral = null
            _connectionState.value = "Disconnected"
        }
    }
}
