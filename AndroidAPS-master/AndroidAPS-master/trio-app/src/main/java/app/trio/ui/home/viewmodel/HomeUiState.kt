package app.trio.ui.home.viewmodel

import java.util.Date

/**
 * HomeUiState
 *
 * Holds the entire state for the Home Module. Mapped directly from iOS HomeStateModel.
 * Features 66+ state properties categorized.
 */
data class HomeUiState(
    // 1. Chart Configuration & Calculated States
    val filteredHours: Int = 24,
    val startMarker: Long = 0L, // Date represented as Long timestamp
    val endMarker: Long = 0L,
    val minForecast: List<Int> = emptyList(),
    val maxForecast: List<Int> = emptyList(),
    val minCount: Int = 12,
    val minYAxisValue: Float = 39f,
    val maxYAxisValue: Float = 200f,
    val minValueCobChart: Float = 0f,
    val maxValueCobChart: Float = 20f,
    val minValueIobChart: Float = 0f,
    val maxValueIobChart: Float = 5f,
    val percentage: Int = 100,
    val hours: Short = 6,
    val selectedTab: Int = 0,
    val waitForSuggestion: Boolean = false,
    val preprocessedData: List<Any> = emptyList(), // Maps to Forecast data
    val isLoopStatusPresented: Boolean = false,
    val isLegendPresented: Boolean = false,
    val shouldDisplayPumpSetupSheet: Boolean = false,
    val shouldDisplayCGMSetupSheet: Boolean = false,

    // 2. User Preferences & Settings (From SettingsManager)
    val units: Int = 0, // Enum representation (0: mgdL, 1: mmolL)
    val bgTargets: Any? = null,
    val targetProfiles: List<Any> = emptyList(),
    val dosingMode: Int = 0, // Enum
    val settingHalfBasalTarget: Float = 160f,
    val lowGlucose: Float = 70f,
    val highGlucose: Float = 180f,
    val currentGlucoseTarget: Float = 100f,
    val glucoseColorScheme: Int = 0,
    val eA1cDisplayUnit: Int = 0,
    val displayXgridLines: Boolean = false,
    val displayYgridLines: Boolean = false,
    val thresholdLines: Boolean = false,
    val bolusDisplayThreshold: Int = 0,
    val forecastDisplayType: Int = 0,
    val showCarbsRequiredBadge: Boolean = true,
    val enableQuickPickTreatments: Boolean = false,
    val highTTraisesSens: Boolean = false,
    val lowTTlowersSens: Boolean = false,
    val isExerciseModeActive: Boolean = false,
    val maxIOB: Float = 0f,
    val autosensMax: Float = 1.2f,
    val isSmoothingEnabled: Boolean = false,
    val pumpInitialSettings: Any? = null,
    val shouldRunDeleteOnSettingsChange: Boolean = true,

    // 3. Sensor & Pump Status
    val timerDate: Long = 0L,
    val isLooping: Boolean = false,
    val lastLoopDate: Long = 0L,
    val statusTitle: String = "",
    val battery: Any? = null,
    val reservoir: Float? = null,
    val pumpName: String = "",
    val pumpExpiresAtDate: Long? = null,
    val pumpActivatedAtDate: Long? = null,
    val errorMessage: String? = null,
    val errorDate: Long? = null,
    val pumpStatusHighlightMessage: String? = null,
    val pumpStatusBadgeImage: Any? = null,
    val pumpStatusBadgeColor: Any? = null,
    val pumpDisplayState: Int? = null,
    val setupPumpEntry: Any? = null,
    val cgmAvailable: Boolean = false,
    val cgmCurrent: Any? = null,
    val listOfCGM: List<Any> = emptyList(),
    val cgmDisplayState: Any? = null,
    val cgmProgressHighlight: Any? = null,
    val cgmSensorExpiresAt: Long? = null,
    val cgmWarmupEndsAt: Long? = null,

    // 4. Mathematical Calculations & Treatment Status
    val bolusProgress: Float? = null,
    val eventualBG: Int? = null,
    val alarm: Any? = null,
    val manualTempBasal: Boolean = false,
    val currentIOB: Float = 0f,
    val totalBolus: Float = 0f,
    val iobProjection: List<Any> = emptyList(),
    val cobProjection: List<Any> = emptyList(),
    val quickPickBolusSuggestions: List<Float> = emptyList(),
    val quickPickCarbSuggestions: List<Float> = emptyList(),
    val bolusStatus: Int = 0, // Enum BolusStatus
    val roundedTotalBolus: String = "",
    val maxBasal: Float = 2f,
    val basalProfile: List<Any> = emptyList(),
    val isOverrideCancelled: Boolean = false
)
