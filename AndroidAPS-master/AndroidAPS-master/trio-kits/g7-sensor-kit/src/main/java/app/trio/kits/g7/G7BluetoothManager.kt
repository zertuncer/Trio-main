package app.trio.kits.g7

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

class G7BluetoothManager {
    private val TAG = "G7BluetoothManager"
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    
    private val scanner = Scanner {
        filters = listOf(Filter.Service(G7BluetoothServices.ADVERTISEMENT_SERVICE))
    }
    
    private var activePeripheral: Peripheral? = null
    
    private val _connectionState = MutableStateFlow<String>("Disconnected")
    val connectionState: StateFlow<String> = _connectionState
    
    fun scanAndConnect(transmitterId: String) {
        _connectionState.value = "Scanning for $transmitterId..."
        Log.d(TAG, "Starting BLE scan for Dexcom G7 (TxID: $transmitterId)")
        
        scope.launch {
            try {
                // Find the peripheral
                val advertisement = scanner.advertisements
                    .catch { e -> Log.e(TAG, "Scan error", e) }
                    .firstOrNull { adv -> 
                        // Typically TxID is embedded in the advertisement name or manufacturer data.
                        // For Trio, we look for Dexcom or specific names.
                        val name = adv.name ?: ""
                        name.contains("Dexcom") || name.endsWith(transmitterId.takeLast(2))
                    }
                    
                if (advertisement != null) {
                    Log.d(TAG, "Found G7: ${advertisement.name}, Mac: ${advertisement.address}")
                    _connectionState.value = "Found ${advertisement.name}. Connecting..."
                    connectToPeripheral(advertisement)
                } else {
                    _connectionState.value = "Not Found"
                    Log.d(TAG, "G7 not found in scan.")
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
            Log.d(TAG, "Peripheral state changed: $state")
            when (state) {
                is State.Connected -> {
                    _connectionState.value = "Connected"
                    // TODO: Authenticate (AuthChallengeRxMessage)
                    // TODO: Request Glucose (G7GlucoseMessage)
                }
                is State.Disconnected -> _connectionState.value = "Disconnected"
                is State.Connecting -> _connectionState.value = "Connecting..."
                is State.Disconnecting -> _connectionState.value = "Disconnecting..."
            }
        }.launchIn(scope)
        
        try {
            peripheral.connect()
        } catch (e: Exception) {
            Log.e(TAG, "Connection failed", e)
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
