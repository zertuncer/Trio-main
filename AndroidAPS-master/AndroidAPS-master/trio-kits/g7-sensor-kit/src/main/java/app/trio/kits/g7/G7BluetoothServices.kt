package app.trio.kits.g7

import java.util.UUID

object G7BluetoothServices {
    val ADVERTISEMENT_SERVICE = UUID.fromString("0000FEBC-0000-1000-8000-00805f9b34fb")
    val CGM_SERVICE = UUID.fromString("F8083532-849E-531C-C594-30F1F86A4EA5")
    
    object Characteristics {
        // Read/Notify
        val COMMUNICATION = UUID.fromString("F8083533-849E-531C-C594-30F1F86A4EA5")
        
        // Write/Indicate
        val CONTROL = UUID.fromString("F8083534-849E-531C-C594-30F1F86A4EA5")
        
        // Write/Indicate
        val AUTHENTICATION = UUID.fromString("F8083535-849E-531C-C594-30F1F86A4EA5")
        
        // Read/Write/Notify
        val BACKFILL = UUID.fromString("F8083536-849E-531C-C594-30F1F86A4EA5")
    }
}
