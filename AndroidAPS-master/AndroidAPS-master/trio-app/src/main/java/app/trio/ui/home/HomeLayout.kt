package app.trio.ui.home

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/// Fixed zone heights for the non-scrolling Home dashboard; the chart takes the remainder.
object HomeLayout {
    /// header slot: pump panel / glucose bobble / loop status; includes room
    /// for the sensor arc/tag overhanging the bobble
    val headerHeight: Dp = 172.dp
    
    /// meal panel slot (IOB / COB / delivery rate)
    val mealSlotHeight: Dp = 44.dp
    
    /// shared slot for adjustment panel and bolus progress (was 8% of screen height)
    val bottomPanelHeight: Dp = 60.dp
    
    /// stats banner slot below the adjustment panel (multi-use panel later)
    val statsBannerHeight: Dp = 60.dp
    
    /// clear air above, between, and below the bottom-zone panels
    val bottomZonePadding: Dp = 10.dp
    
    val bottomZoneHeight: Dp
        get() = bottomPanelHeight + statsBannerHeight + (bottomZonePadding * 3)
        
    /// pull distance that triggers the forced loop
    val refreshTriggerDistance: Dp = 70.dp
    
    /// indicator row height while the loop runs
    val refreshIndicatorHeight: Dp = 40.dp
    
    /// last-resort chart floor; must stay below the tightest natural allocation
    /// (SE under the iOS 26 tab bar + accessory leaves ~140) or the bottom zone overflows
    val chartMinHeight: Dp = 130.dp
}
