package app.trio.kits.g7

import app.aaps.core.data.plugin.PluginType
import app.aaps.core.interfaces.plugin.PluginBase
import app.aaps.core.interfaces.source.BgSource

class G7Plugin(
    private val bluetoothManager: G7BluetoothManager
) : PluginBase, BgSource {
    
    override val name: String = "Dexcom G7 (Trio Native)"
    
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
    override fun advancedFilteringSupported(): Boolean = true
    
    override val sensorBatteryLevel: Int
        get() = -1 // G7 doesn't report battery level like G6
        
    fun onTransmitterIdEntered(transmitterId: String) {
        bluetoothManager.scanAndConnect(transmitterId)
    }
    
    fun stopSensor() {
        bluetoothManager.disconnect()
    }
}
