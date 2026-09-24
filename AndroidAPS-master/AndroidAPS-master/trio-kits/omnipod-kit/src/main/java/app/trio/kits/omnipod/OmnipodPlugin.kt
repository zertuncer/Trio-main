package app.trio.kits.omnipod

import app.aaps.core.data.plugin.PluginType
import app.aaps.core.interfaces.plugin.PluginBase
import app.aaps.core.interfaces.pump.Pump

class OmnipodPlugin(
    private val bluetoothManager: OmnipodBluetoothManager
) : PluginBase, Pump {
    
    override val name: String = "Omnipod (Trio Native)"
    
    override fun getType(): PluginType = PluginType.PUMP
    
    override fun isDefault(): Boolean = false
    
    override fun isEnabled(type: PluginType): Boolean = true
    
    override fun setPluginEnabled(type: PluginType, enabled: Boolean) {
        // Handle enable/disable
    }
    
    override fun showInList(type: PluginType): Boolean = true
    
    override fun setFragmentVisible(type: PluginType, visible: Boolean) {
        // For UI visibility if needed in AAPS config builder
    }
    
    // Pump implementation basics
    override val isTempBasalSupported: Boolean = true
    override val isExtendedBolusSupported: Boolean = true
    override val isTbrSupported: Boolean = true
    override val isTbrPercentSupported: Boolean = false // Pods use absolute TBR mostly
    
    fun onPodPaired(podId: String) {
        bluetoothManager.scanAndConnect(podId)
    }
    
    fun deactivatePod() {
        bluetoothManager.disconnect()
    }
}
