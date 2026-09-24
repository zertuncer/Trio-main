package app.trio.kits.libre

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

class LibreBluetoothManager {
    private val TAG = "LibreBluetoothManager"
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    
    // Custom Libre UUIDs
    private val LIBRE_SERVICE_UUID = UUID.fromString("0000FDE3-0000-1000-8000-00805F9B34FB")
    
    private val scanner = Scanner {
        filters = listOf(Filter.Service(LIBRE_SERVICE_UUID))
    }
    
    private var activePeripheral: Peripheral? = null
    
    private val _connectionState = MutableStateFlow<String>("Disconnected")
    val connectionState: StateFlow<String> = _connectionState
    
    fun scanAndConnect(sensorMac: String) {
        _connectionState.value = "Scanning for Libre ($sensorMac)..."
        Log.d(TAG, "Starting BLE scan for FreeStyle Libre (MAC: $sensorMac)")
        
        scope.launch {
            try {
                val advertisement = scanner.advertisements
                    .catch { e -> Log.e(TAG, "Scan error", e) }
                    .firstOrNull { adv -> 
                        adv.address.equals(sensorMac, ignoreCase = true) || adv.name?.contains("ABBOTT") == true
                    }
                    
                if (advertisement != null) {
                    Log.d(TAG, "Found Libre: ${advertisement.name}, Mac: ${advertisement.address}")
                    _connectionState.value = "Found ${advertisement.name}. Connecting..."
                    connectToPeripheral(advertisement)
                } else {
                    _connectionState.value = "Not Found"
                    Log.d(TAG, "Libre not found in scan.")
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
            Log.d(TAG, "Libre state changed: $state")
            when (state) {
                is State.Connected -> {
                    _connectionState.value = "Connected"
                    // TODO: Implement Libre Unlock and Streaming
                }
                is State.Disconnected -> _connectionState.value = "Disconnected"
                is State.Connecting -> _connectionState.value = "Connecting..."
                is State.Disconnecting -> _connectionState.value = "Disconnecting..."
            }
        }.launchIn(scope)
        
        try {
            peripheral.connect()
        } catch (e: Exception) {
            Log.e(TAG, "Libre Connection failed", e)
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
