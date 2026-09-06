@file:Suppress("UnusedPrivateMember")

package com.example.watermarkoverlay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.backdrops.rememberCanvasBackdrop
import com.kyant.backdrop.catalog.components.LiquidSlider
import top.yukonga.miuix.kmp.basic.Button
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextField
import top.yukonga.miuix.kmp.theme.ColorSchemeMode
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.theme.ThemeController

@Composable
fun WatermarkApp(
    settings: WatermarkSettings,
    overlayPermissionGranted: Boolean,
    watermarkRunning: Boolean,
    onSettingsChange: (WatermarkSettings) -> Unit,
    onRequestOverlayPermission: () -> Unit,
    onStartWatermark: () -> Unit,
    onStopWatermark: () -> Unit,
) {
    val themeController = remember { ThemeController(ColorSchemeMode.System) }
    MiuixTheme(controller = themeController) {
        WatermarkScreen(
            settings = settings,
            overlayPermissionGranted = overlayPermissionGranted,
            watermarkRunning = watermarkRunning,
            onSettingsChange = onSettingsChange,
            onRequestOverlayPermission = onRequestOverlayPermission,
            onStartWatermark = onStartWatermark,
            onStopWatermark = onStopWatermark,
        )
    }
}

@Composable
private fun WatermarkScreen(
    settings: WatermarkSettings,
    overlayPermissionGranted: Boolean,
    watermarkRunning: Boolean,
    onSettingsChange: (WatermarkSettings) -> Unit,
    onRequestOverlayPermission: () -> Unit,
    onStartWatermark: () -> Unit,
    onStopWatermark: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(text = stringResource(R.string.watermark_title))

        Text(
            text = stringResource(
                if (overlayPermissionGranted) {
                    R.string.overlay_permission_status_granted
                } else {
                    R.string.overlay_permission_status_denied
                }
            )
        )

        if (!overlayPermissionGranted) {
            Button(onClick = onRequestOverlayPermission) {
                Text(stringResource(R.string.grant_overlay_permission))
            }
        }

        Text(
            text = stringResource(
                if (watermarkRunning) {
                    R.string.running_status_running
                } else {
                    R.string.running_status_stopped
                }
            )
        )

        TextField(
            value = settings.lineOne,
            onValueChange = { onSettingsChange(settings.copy(lineOne = it)) },
            label = stringResource(R.string.first_line_text),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        TextField(
            value = settings.lineTwo,
            onValueChange = { onSettingsChange(settings.copy(lineTwo = it)) },
            label = stringResource(R.string.second_line_text),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        SettingSlider(
            label = stringResource(R.string.font_size, settings.textSizeSp),
            value = settings.textSizeSp.toFloat(),
            valueRange = 12f..40f,
            steps = 27,
        ) {
            onSettingsChange(settings.copy(textSizeSp = it.toInt()))
        }

        SettingSlider(
            label = stringResource(R.string.opacity, settings.opacityPercent),
            value = settings.opacityPercent.toFloat(),
            valueRange = 10f..100f,
            steps = 89,
        ) {
            onSettingsChange(settings.copy(opacityPercent = it.toInt()))
        }

        SettingSlider(
            label = stringResource(R.string.spacing, settings.spacingDp),
            value = settings.spacingDp.toFloat(),
            valueRange = 20f..200f,
            steps = 179,
        ) {
            onSettingsChange(settings.copy(spacingDp = it.toInt()))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Button(
                onClick = onStartWatermark,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColorsPrimary(),
            ) {
                Text(stringResource(R.string.start_watermark))
            }

            Button(
                onClick = onStopWatermark,
                modifier = Modifier.weight(1f),
                enabled = watermarkRunning,
            ) {
                Text(stringResource(R.string.stop_watermark))
            }
        }
    }
}

@Composable
private fun SettingSlider(
    label: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int,
    onValueChange: (Float) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = label)

        LiquidSlider(
            value = { value },
            onValueChange = onValueChange,
            valueRange = valueRange,
            visibilityThreshold = 0.01f,
            backdrop = rememberCanvasBackdrop {
                drawRect(color = Color.White)
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
