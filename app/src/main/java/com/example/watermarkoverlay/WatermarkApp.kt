package com.example.watermarkoverlay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Button
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Slider
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
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(text = "悬浮水印")
        Text(text = "悬浮窗权限：${if (overlayPermissionGranted) "已授予" else "未授予"}")
        if (!overlayPermissionGranted) {
            Button(onClick = onRequestOverlayPermission) { Text("授予悬浮窗权限") }
        }
        Text(text = "运行状态：${if (watermarkRunning) "运行中" else "未启动"}")
        TextField(
            value = settings.lineOne,
            onValueChange = { onSettingsChange(settings.copy(lineOne = it)) },
            label = "第一行文字",
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        TextField(
            value = settings.lineTwo,
            onValueChange = { onSettingsChange(settings.copy(lineTwo = it)) },
            label = "第二行文字",
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        SettingSlider("字体大小：${settings.textSizeSp} sp", settings.textSizeSp.toFloat(), 12f..40f, 27) {
            onSettingsChange(settings.copy(textSizeSp = it.toInt()))
        }
        SettingSlider("透明度：${settings.opacityPercent}%", settings.opacityPercent.toFloat(), 10f..100f, 89) {
            onSettingsChange(settings.copy(opacityPercent = it.toInt()))
        }
        SettingSlider("组间距：${settings.spacingDp} dp", settings.spacingDp.toFloat(), 20f..200f, 179) {
            onSettingsChange(settings.copy(spacingDp = it.toInt()))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = onStartWatermark,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColorsPrimary(),
            ) { Text("启动水印") }
            Button(onClick = onStopWatermark, modifier = Modifier.weight(1f), enabled = watermarkRunning) {
                Text("停止水印")
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
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
