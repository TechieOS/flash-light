package com.techieos.flash_light.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.techieos.flash_light.infrastructure.FlashlightManager
import com.techieos.flash_light.infrastructure.SensorProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class FlashlightViewModel(
    private val flashlightManager: FlashlightManager,
    private val sensorProvider: SensorProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(FlashlightUiState())
    val uiState: StateFlow<FlashlightUiState> = _uiState.asStateFlow()

    init {
        // Collect sensor data reactively as per acceptance criteria
        sensorProvider.getLightLevel()
            .onEach { lux ->
                _uiState.update { it.copy(currentLux = lux) }
                handleAutoDarkMode(lux)
            }
            .launchIn(viewModelScope)
    }

    /**
     * Toggles the torch manually.
     */
    fun toggleManualTorch() {
        _uiState.update { currentState ->
            val newState = !currentState.isTorchOn
            flashlightManager.toggleTorch(newState)
            currentState.copy(isTorchOn = newState)
        }
    }

    /**
     * Toggles the auto-dark mode. When enabled, the torch will be controlled by ambient light levels.
     */
    fun toggleAutoDarkMode() {
        _uiState.update { currentState ->
            val newState = !currentState.isAutoDarkModeEnabled
            val updatedState = currentState.copy(isAutoDarkModeEnabled = newState)
            
            // Immediately apply auto-dark mode logic if it was just enabled
            if (newState) {
                val shouldBeOn = updatedState.currentLux < LUX_THRESHOLD
                if (shouldBeOn != updatedState.isTorchOn) {
                    flashlightManager.toggleTorch(shouldBeOn)
                    return@update updatedState.copy(isTorchOn = shouldBeOn)
                }
            }
            updatedState
        }
    }

    private fun handleAutoDarkMode(lux: Float) {
        if (_uiState.value.isAutoDarkModeEnabled) {
            val shouldBeOn = lux < LUX_THRESHOLD
            if (shouldBeOn != _uiState.value.isTorchOn) {
                flashlightManager.toggleTorch(shouldBeOn)
                _uiState.update { it.copy(isTorchOn = shouldBeOn) }
            }
        }
    }

    companion object {
        private const val LUX_THRESHOLD = 10f
    }
}

/**
 * UI State for the Flashlight screen.
 */
data class FlashlightUiState(
    val isTorchOn: Boolean = false,
    val isAutoDarkModeEnabled: Boolean = false,
    val currentLux: Float = 0f
)

/**
 * Factory for creating [FlashlightViewModel] with its dependencies.
 */
class FlashlightViewModelFactory(
    private val flashlightManager: FlashlightManager,
    private val sensorProvider: SensorProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashlightViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlashlightViewModel(flashlightManager, sensorProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
