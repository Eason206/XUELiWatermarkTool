package com.example.watermarkoverlay

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.catalog.components.LiquidBottomTab
import com.kyant.backdrop.catalog.components.LiquidBottomTabs
import top.yukonga.miuix.kmp.basic.Text

class MainActivity : ComponentActivity() {
    private val notificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    private var watermarkSettings by mutableStateOf(
        WatermarkSettings("26847365912", "864927051738426", 18, 35, 54)
    )
    private var overlayPermissionGranted by mutableStateOf(false)
    private var watermarkRunning by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        watermarkSettings = SettingsStore.load(this)
        refreshSystemState()
        setContent {
            var selectedTab by remember { mutableIntStateOf(0) }

            val backgroundColor = Color.White
            val backdrop = rememberLayerBackdrop {
                drawRect(backgroundColor)
                drawContent()
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .layerBackdrop(backdrop)
                ) {
                    if (selectedTab == 0) {
                        WatermarkApp(
                            settings = watermarkSettings,
                            overlayPermissionGranted = overlayPermissionGranted,
                            watermarkRunning = watermarkRunning,
                            onSettingsChange = { watermarkSettings = it },
                            onRequestOverlayPermission = ::requestOverlayPermission,
                            onStartWatermark = ::startOverlay,
                            onStopWatermark = ::stopOverlay,
                        )
                    } else {
                        AboutScreen()
                    }
                }

                LiquidBottomTabs(
                    selectedTabIndex = { selectedTab },
                    onTabSelected = { selectedTab = it },
                    tabsCount = 2,
                    backdrop = backdrop,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                ) {
                    LiquidBottomTab(
                        onClick = { selectedTab = 0 },
                    ) {
                        Text(getString(R.string.home))
                    }

                    LiquidBottomTab(
                        onClick = { selectedTab = 1 },
                    ) {
                        Text(getString(R.string.about))
                    }
                }
            }
        }
        if (Build.VERSION.SDK_INT >= 33) {
            notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshSystemState()
    }

    override fun onPause() {
        SettingsStore.save(this, watermarkSettings)
        super.onPause()
    }

    private fun refreshSystemState() {
        overlayPermissionGranted = Settings.canDrawOverlays(this)
        watermarkRunning = WatermarkService.isRunning
    }

    private fun requestOverlayPermission() {
        startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")))
    }

    private fun startOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            requestOverlayPermission()
            return
        }
        SettingsStore.save(this, watermarkSettings)
        val serviceIntent = Intent(this, WatermarkService::class.java)
        if (Build.VERSION.SDK_INT >= 26) startForegroundService(serviceIntent) else startService(serviceIntent)
        watermarkRunning = true
    }

    private fun stopOverlay() {
        stopService(Intent(this, WatermarkService::class.java))
        watermarkRunning = false
    }
}



