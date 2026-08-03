package com.techieos.flash_light

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.techieos.flash_light.infrastructure.FlashlightManager
import com.techieos.flash_light.infrastructure.SensorProvider
import com.techieos.flash_light.ui.navigation.Route
import com.techieos.flash_light.ui.screens.DashboardScreen
import com.techieos.flash_light.ui.theme.FlashlightTheme
import com.techieos.flash_light.ui.viewmodel.FlashlightViewModel
import com.techieos.flash_light.ui.viewmodel.FlashlightViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val flashlightManager = FlashlightManager(this)
        val sensorProvider = SensorProvider(this)

        setContent {
            FlashlightTheme {
                FlashlightApp(flashlightManager, sensorProvider)
            }
        }
    }
}

@Composable
fun FlashlightApp(
    flashlightManager: FlashlightManager,
    sensorProvider: SensorProvider
) {
    val backStack = remember { mutableStateListOf<Route>(Route.Dashboard) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.Dashboard> {
                val viewModel: FlashlightViewModel = viewModel(
                    factory = remember {
                        FlashlightViewModelFactory(flashlightManager, sensorProvider)
                    }
                )
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                DashboardScreen(
                    uiState = uiState,
                    onToggleManual = viewModel::toggleManualTorch,
                    onToggleAuto = viewModel::toggleAutoDarkMode
                )
            }
        }
    )
}
