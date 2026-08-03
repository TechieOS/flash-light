package com.techieos.flash_light.ui.viewmodel

import com.techieos.flash_light.infrastructure.FlashlightManager
import com.techieos.flash_light.infrastructure.SensorProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FlashlightViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    
    private lateinit var viewModel: FlashlightViewModel
    private lateinit var fakeFlashlightManager: FakeFlashlightManager
    private lateinit var fakeSensorProvider: FakeSensorProvider

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeFlashlightManager = FakeFlashlightManager()
        fakeSensorProvider = FakeSensorProvider()
        viewModel = FlashlightViewModel(fakeFlashlightManager, fakeSensorProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() = runTest {
        val state = viewModel.uiState.value
        assertFalse(state.isTorchOn)
        assertFalse(state.isAutoDarkModeEnabled)
        assertEquals(0f, state.currentLux)
    }

    @Test
    fun `toggleManualTorch toggles torch and updates state`() = runTest {
        viewModel.toggleManualTorch()
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value.isTorchOn)
        assertTrue(fakeFlashlightManager.isTorchOn)

        viewModel.toggleManualTorch()
        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isTorchOn)
        assertFalse(fakeFlashlightManager.isTorchOn)
    }

    @Test
    fun `sensor updates lux value in state`() = runTest {
        fakeSensorProvider.emitLux(15f)
        advanceUntilIdle()
        assertEquals(15f, viewModel.uiState.value.currentLux)
    }

    @Test
    fun `auto-dark mode triggers torch when lux is low`() = runTest {
        viewModel.toggleAutoDarkMode()
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value.isAutoDarkModeEnabled)

        fakeSensorProvider.emitLux(5f)
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value.isTorchOn)
        assertTrue(fakeFlashlightManager.isTorchOn)

        fakeSensorProvider.emitLux(15f)
        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isTorchOn)
        assertFalse(fakeFlashlightManager.isTorchOn)
    }

    @Test
    fun `auto-dark mode does nothing when disabled`() = runTest {
        // Disabled by default
        fakeSensorProvider.emitLux(5f)
        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isTorchOn)
        assertFalse(fakeFlashlightManager.isTorchOn)
    }

    class FakeFlashlightManager : FlashlightManager(null) {
        var isTorchOn = false
        override fun toggleTorch(enabled: Boolean) {
            isTorchOn = enabled
        }
    }

    class FakeSensorProvider : SensorProvider(null) {
        private val luxFlow = MutableSharedFlow<Float>()
        override fun getLightLevel(): Flow<Float> = luxFlow
        suspend fun emitLux(value: Float) {
            luxFlow.emit(value)
        }
    }
}
