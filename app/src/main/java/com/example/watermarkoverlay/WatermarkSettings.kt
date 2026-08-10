package com.example.watermarkoverlay

import android.content.Context

data class WatermarkSettings(val lineOne: String, val lineTwo: String, val textSizeSp: Int, val opacityPercent: Int, val spacingDp: Int)

object SettingsStore {
    private const val PREFS = "watermark_settings"
    fun load(context: Context): WatermarkSettings {
        val p = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return WatermarkSettings(p.getString("line_one", "26847365912").orEmpty(), p.getString("line_two", "864927051738426").orEmpty(), p.getInt("text_size", 18), p.getInt("opacity", 35), p.getInt("spacing", 54))
    }
    fun save(context: Context, s: WatermarkSettings) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putString("line_one", s.lineOne).putString("line_two", s.lineTwo).putInt("text_size", s.textSizeSp).putInt("opacity", s.opacityPercent).putInt("spacing", s.spacingDp).apply()
    }
}
