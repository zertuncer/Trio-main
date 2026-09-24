package app.trio.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Mock dependencies for Phase 2 UI development
class MockSettingsProvider : ISettingsProvider {
    override fun getMaxBolusUnits(): Double = 10.0
    override fun getMaxCarbs(): Double = 120.0
}

class MockUnlockManager : IUnlockManager {
    override suspend fun unlock(): Boolean = true // Mocked success
}

class MockApsManager : IApsManager {
    override suspend fun enactBolus(amount: Double, isSMB: Boolean): Pair<Boolean, String?> {
        return Pair(true, null)
    }
}

class MockCarbsStorage : ICarbsStorage {
    override suspend fun storeCarbs(carbs: Double): Boolean = true
}

class MockRoomAuditLogger : IRoomAuditLogger {
    override suspend fun logQuickPickTreatment(
        requestedBolus: Float?, cappedBolus: Double?, maxBolusLimit: Double,
        requestedCarbs: Float?, cappedCarbs: Double?, maxCarbLimit: Double,
        success: Boolean, message: String?
    ) {
        // Will be replaced by actual Room DAO insert in Phase 3
    }
}

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val quickPickHandler = QuickPickTreatmentsHandler(
        unlockManager = MockUnlockManager(),
        apsManager = MockApsManager(),
        settingsProvider = MockSettingsProvider(),
        carbsStorage = MockCarbsStorage(),
        auditLogger = MockRoomAuditLogger()
    )

    init {
        startPeriodicTimer()
    }

    private fun startPeriodicTimer() {
        viewModelScope.launch {
            while (true) {
                _uiState.value = _uiState.value.copy(
                    timerDate = System.currentTimeMillis()
                )
                delay(30_000)
            }
        }
    }

    fun loadQuickPickTreatmentSuggestions(
        historicalBoluses: List<QuickPickSample>,
        historicalCarbs: List<QuickPickSample>
    ) {
        val bolusSuggestions = quickPickHandler.topQuickPickSuggestions(historicalBoluses)
        val carbSuggestions = quickPickHandler.topQuickPickSuggestions(historicalCarbs)

        _uiState.value = _uiState.value.copy(
            quickPickBolusSuggestions = bolusSuggestions,
            quickPickCarbSuggestions = carbSuggestions
        )
    }

    fun enactQuickPickTreatment(bolusAmount: Float?, carbAmount: Float?) {
        viewModelScope.launch {
            val outcome = quickPickHandler.enactQuickPickTreatment(bolusAmount, carbAmount)
            // Handle outcome (e.g. show snackbar if bolusFailureMessage != null)
        }
    }
}
