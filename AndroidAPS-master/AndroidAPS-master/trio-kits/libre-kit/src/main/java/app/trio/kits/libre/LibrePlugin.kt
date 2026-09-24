package app.trio.kits.libre

import app.aaps.core.data.plugin.PluginType
import app.aaps.core.interfaces.plugin.PluginBase
import app.aaps.core.interfaces.source.BgSource

class LibrePlugin(
    private val bluetoothManager: LibreBluetoothManager
) : PluginBase, BgSource {
    
    override val name: String = "FreeStyle Libre (Trio Native)"
    
    override fun getType(): PluginType = PluginType.BGSOURCE
    
    override fun isDefault(): Boolean = false
    
    override fun isEnabled(type: PluginType): Boolean = true
    
    override fun setPluginEnabled(type: PluginType, enabled: Boolean) {
        // Handle enable/disable
    }
    
    override fun showInList(type: PluginType): Boolean = true
    
    override fun setFragmentVisible(type: PluginType, visible: Boolean) {
        // For UI visibility if needed in AAPS config builder
    }
    
    // BgSource implementation
    override fun advancedFilteringSupported(): Boolean = false
    
    override val sensorBatteryLevel: Int
        get() = -1 // Derived from NFC memory, not strictly BLE
        
    fun onSensorMacEntered(macAddress: String) {
        bluetoothManager.scanAndConnect(macAddress)
    }
    
    fun stopSensor() {
        bluetoothManager.disconnect()
    }
}
