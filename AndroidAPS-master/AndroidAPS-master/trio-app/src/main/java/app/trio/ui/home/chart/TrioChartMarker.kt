package app.trio.ui.home.chart

import android.graphics.RectF
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.patrykandpatrick.vico.core.chart.dimensions.HorizontalDimensions
import com.patrykandpatrick.vico.core.context.DrawContext
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.chart.values.ChartValuesProvider
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.marker.MarkerLabelFormatter

class TrioChartMarker(
    private val delegate: Marker,
    private val onPositionChanged: (x: Float, y: Float) -> Unit
) : Marker {
    override fun draw(
        context: DrawContext,
        bounds: RectF,
        markedEntries: List<Marker.EntryModel>,
        chartValuesProvider: ChartValuesProvider
    ) {
        if (markedEntries.isNotEmpty()) {
            val entry = markedEntries.first().entry
            onPositionChanged(entry.x, entry.y)
        }
        delegate.draw(context, bounds, markedEntries, chartValuesProvider)
    }

    override fun getInsets(
        context: MeasureContext,
        outInsets: Insets,
        horizontalDimensions: HorizontalDimensions
    ) = delegate.getInsets(context, outInsets, horizontalDimensions)
}

@Composable
fun rememberTrioChartMarker(onPositionChanged: (x: Float, y: Float) -> Unit): Marker {
    val indicator = remember {
        ShapeComponent(shape = Shapes.pillShape, color = Color(0xFF007AFF).toArgb()).apply {
            setShadow(radius = 4f, dy = 2f, color = 0x40000000)
        }
    }
    
    val guideline = remember {
        LineComponent(
            color = Color.Gray.copy(alpha = 0.5f).toArgb(),
            thicknessDp = 2f,
            shape = DashedShape(shape = Shapes.rectShape, dashLengthDp = 4f, gapLengthDp = 4f)
        )
    }
    
    val label = remember {
        TextComponent.Builder().apply {
            color = Color.Transparent.toArgb()
            background = null
            padding = com.patrykandpatrick.vico.core.dimensions.MutableDimensions(0f, 0f, 0f, 0f)
        }.build()
    }
    
    val markerComponent = remember(label, indicator, guideline) {
        MarkerComponent(label, indicator, guideline).apply {
            labelFormatter = MarkerLabelFormatter { _, _ -> "" }
        }
    }
    
    return remember(markerComponent, onPositionChanged) {
        TrioChartMarker(markerComponent, onPositionChanged)
    }
}
