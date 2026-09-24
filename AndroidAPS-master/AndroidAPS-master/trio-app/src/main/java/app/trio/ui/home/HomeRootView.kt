package app.trio.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.lifecycle.viewmodel.compose.viewModel
import app.trio.ui.home.viewmodel.HomeViewModel

// Trio iOS: struct RootView: BaseView
@Composable
fun HomeRootView(
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Local Component States
    var showPumpSelection by remember { mutableStateOf(false) }
    var showCGMSelection by remember { mutableStateOf(false) }
    
    // Marker / Scrub (Readout) States
    // Marker / Scrub (Readout) States
    var chartReadoutDate by remember { mutableStateOf<Float?>(null) }
    var chartReadoutGlucose by remember { mutableStateOf<Float?>(null) }
    var isChartReadoutVisible by remember { mutableStateOf(false) }

    // Decay Timer (iOS parite)
    class DecayJobHolder { var job: kotlinx.coroutines.Job? = null }
    val decayJobHolder = remember { DecayJobHolder() }
    val coroutineScope = rememberCoroutineScope()
    
    // Not: Tüm UI aksiyonları ViewModel Flow'u (uiState) üzerinden işlenmelidir.
    
    var showSnoozeSheet by remember { mutableStateOf(false) }
    var showManualGlucose by remember { mutableStateOf(false) }
    var showReleaseNotes by remember { mutableStateOf(false) }
    
    var pullOffset by remember { mutableStateOf(0f) }
    var isRefreshArmed by remember { mutableStateOf(false) }
    var isForcingLoop by remember { mutableStateOf(false) }
    var notificationsDisabled by remember { mutableStateOf(false) }
    
    // CoreData FetchRequests (Sonradan motora bağlanacak)
    // @FetchRequest(...) var latestOverride
    // @FetchRequest(...) var latestTempTarget

    // iOS Ana ZStack ve ViewBuilder Mantığının Birebir Klonu
    Box(
        modifier = Modifier
            .fillMaxSize()
            // appState.trioBackgroundColor(for: colorScheme)
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // iOS: dashboardContent(geo) (Scrollable area)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // Header (Pump Panel, Glucose Bobble, Loop Status)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(HomeLayout.headerHeight)
                ) {
                    // right panel with loop status and evBG (Trio iOS line 196)
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 20.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RightHeaderPanel()
                    }

                    // glucose bobble (Trio iOS line 202)
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                if (!uiState.cgmAvailable) {
                                    showCGMSelection = true // Add CGM menu
                                } else {
                                    // Open CGM Settings (shouldDisplayCGMSetupSheet)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        GlucoseView()
                    }

                    // left panel with pump related info (Trio iOS line 205)
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 20.dp)
                            .clickable {
                                if (uiState.pumpDisplayState == null) {
                                    showPumpSelection = true // Add Pump menu
                                } else {
                                    // Open Pump Settings (shouldDisplayPumpSetupSheet)
                                }
                            },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PumpView()
                    }
                }
                
                // Meal Slot (Trio iOS line 215)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(HomeLayout.mealSlotHeight)
                ) {
                    MealPanel(
                        isChartReadoutVisible = isChartReadoutVisible,
                        chartReadoutDate = chartReadoutDate,
                        chartReadoutGlucose = chartReadoutGlucose
                    )
                }
                
                // Main Chart
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Kalan tüm boşluğu doldur
                        .heightIn(min = HomeLayout.chartMinHeight)
                ) {
                    app.trio.ui.home.chart.MainChartView(
                        modifier = Modifier.fillMaxSize(),
                        onMarkerVisibilityChanged = { isVisible -> 
                            if (isVisible) {
                                isChartReadoutVisible = true
                                decayJobHolder.job?.cancel() // Gerçek veri bölgesinde açılıyorsa timer'ı iptal et
                            } else {
                                // Parmak kaldırıldı: 600ms decay başlat
                                decayJobHolder.job?.cancel()
                                decayJobHolder.job = coroutineScope.launch {
                                    delay(600)
                                    isChartReadoutVisible = false
                                }
                            }
                        },
                        onMarkerPositionChanged = { x, y, isReal -> 
                            if (isReal) {
                                // Gerçek veri: State'i güncelle ve timer'ı iptal et (decay'i durdur)
                                chartReadoutDate = x
                                chartReadoutGlucose = y
                                isChartReadoutVisible = true
                                decayJobHolder.job?.cancel()
                            } else {
                                // Cone (tahmin) bölgesi: State'i dondur, decay timer'ı sıfırla/başlat
                                decayJobHolder.job?.cancel()
                                decayJobHolder.job = coroutineScope.launch {
                                    delay(600)
                                    isChartReadoutVisible = false
                                }
                            }
                        }
                    )
                }
            }
            
            // iOS: safeAreaInset(edge: .bottom) -> bottomControls()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(HomeLayout.bottomZoneHeight)
            ) {
                BottomControls()
            }
        }
        
        // PF-03: Scrim overlay instead of Blur for sheets (e.g. isLoopStatusPresented)
        if (uiState.isLoopStatusPresented) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)) // Dimmed overlay
            )
        }
    }
}

// Alt bileşenler artık kendi dosyalarında (RightHeaderPanel.kt, GlucoseView.kt, PumpView.kt, MealPanel.kt, BottomControls.kt)
