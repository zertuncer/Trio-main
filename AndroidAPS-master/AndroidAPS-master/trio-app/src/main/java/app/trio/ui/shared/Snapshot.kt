package app.trio.ui.shared

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

/**
 * Orijinal iOS View+Snapshot eşdeğeri.
 * Android Compose'da doğrudan View'i bitmape dönüştürmek için
 * Compose 1.7.0 `captureController` veya Android View tabanlı drawToBitmap() kullanılır.
 * Bu sınıf şimdilik API yüzeyini (helper) sağlar.
 */
object Snapshot {
    /**
     * Android View nesnesini Bitmap'e dönüştürür.
     */
    fun takeSnapshot(view: View): ImageBitmap {
        val bitmap = Bitmap.createBitmap(
            view.width, view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap.asImageBitmap()
    }
}
