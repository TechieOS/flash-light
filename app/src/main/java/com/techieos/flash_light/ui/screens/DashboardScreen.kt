package com.techieos.flash_light.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashlightOff
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.techieos.flash_light.ui.theme.FlashlightTheme
import com.techieos.flash_light.ui.viewmodel.FlashlightUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    uiState: FlashlightUiState,
    onToggleManual: () -> Unit,
    onToggleAuto: () -> Unit,
    modifier: Modifier = Modifier
) {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val isCompact = adaptiveInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Flashlight", fontWeight = FontWeight.Bold) }
            )
        }
    ) { innerPadding ->
        if (isCompact) {
            DashboardContentCompact(
                uiState = uiState,
                onToggleManual = onToggleManual,
                onToggleAuto = onToggleAuto,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            DashboardContentExpanded(
                uiState = uiState,
                onToggleManual = onToggleManual,
                onToggleAuto = onToggleAuto,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun DashboardContentCompact(
    uiState: FlashlightUiState,
    onToggleManual: () -> Unit,
    onToggleAuto: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        LuxDisplay(lux = uiState.currentLux)

        TorchButton(
            isTorchOn = uiState.isTorchOn,
            onClick = onToggleManual,
            modifier = Modifier.size(200.dp)
        )

        AutoModeToggle(
            isEnabled = uiState.isAutoDarkModeEnabled,
            onToggle = onToggleAuto
        )
    }
}

@Composable
fun DashboardContentExpanded(
    uiState: FlashlightUiState,
    onToggleManual: () -> Unit,
    onToggleAuto: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LuxDisplay(lux = uiState.currentLux)
            Spacer(modifier = Modifier.height(32.dp))
            AutoModeToggle(
                isEnabled = uiState.isAutoDarkModeEnabled,
                onToggle = onToggleAuto
            )
        }

        TorchButton(
            isTorchOn = uiState.isTorchOn,
            onClick = onToggleManual,
            modifier = Modifier.size(300.dp)
        )
    }
}

@Composable
fun LuxDisplay(lux: Float) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Default.LightMode,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = String.format("%.1f Lux", lux),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Ambient Light",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun TorchButton(
    isTorchOn: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor by animateColorAsState(
        targetValue = if (isTorchOn) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        label = "ButtonColor"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isTorchOn) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "ContentColor"
    )
    val elevation by animateDpAsState(
        targetValue = if (isTorchOn) 0.dp else 8.dp,
        label = "Elevation"
    )

    Surface(
        onClick = onClick,
        modifier = modifier.clip(CircleShape),
        color = containerColor,
        tonalElevation = elevation,
        shadowElevation = elevation
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = if (isTorchOn) Icons.Default.FlashlightOn else Icons.Default.FlashlightOff,
                    contentDescription = if (isTorchOn) "Turn Off" else "Turn On",
                    modifier = Modifier.size(64.dp),
                    tint = contentColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isTorchOn) "ON" else "OFF",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = contentColor
                )
            }
        }
    }
}

@Composable
fun AutoModeToggle(
    isEnabled: Boolean,
    onToggle: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = Modifier.widthIn(min = 200.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Auto-Dark Mode",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isEnabled) "Active" else "Inactive",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Switch(
                checked = isEnabled,
                onCheckedChange = { onToggle() }
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun DashboardScreenPreview() {
    FlashlightTheme {
        DashboardScreen(
            uiState = FlashlightUiState(isTorchOn = false, isAutoDarkModeEnabled = true, currentLux = 5.2f),
            onToggleManual = {},
            onToggleAuto = {}
        )
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun DashboardScreenExpandedPreview() {
    FlashlightTheme {
        DashboardScreen(
            uiState = FlashlightUiState(isTorchOn = true, isAutoDarkModeEnabled = false, currentLux = 150f),
            onToggleManual = {},
            onToggleAuto = {}
        )
    }
}
