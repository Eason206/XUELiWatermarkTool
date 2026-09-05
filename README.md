<div align="center">

<img src="app/src/main/res/drawable/ic_launcher.png" width="128" alt="XUELi Watermark Tool">

# XUELi Watermark Tool

A lightweight Android floating watermark tool.

[![Release](https://img.shields.io/github/v/release/Eason206/XUELiWatermarkTool?style=flat-square)](https://github.com/Eason206/XUELiWatermarkTool/releases)
[![Android](https://img.shields.io/badge/Android-8.0%2B-green?style=flat-square)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.20-7F52FF?style=flat-square)](https://kotlinlang.org/)

</div>

## About

**XUELi Watermark Tool** 是一款轻量级 Android 悬浮水印工具，用于在屏幕上显示自定义双行文字水印。

目前版本为 **2.0.0**，采用全新的现代化 UI，并加入 Liquid Glass 风格交互。

XUELi Watermark Tool is a lightweight Android floating watermark application that displays customizable two-line text overlays.

Version **2.0.0** introduces a completely redesigned interface with a modern visual style and Liquid Glass-inspired interactions.

## Features

* Custom two-line watermark / 自定义双行水印
* Adjustable font size / 可调字体大小
* Adjustable opacity / 可调透明度
* Adjustable line spacing / 可调间距
* Floating overlay / 悬浮窗显示
* Non-interactive overlay / 不拦截下方应用触摸操作
* Modern Compose UI / 现代化 Compose UI
* Liquid Glass-inspired interactions / Liquid Glass 风格交互
* Android 8.0+ support / 支持 Android 8.0+

## Screenshots

> Screenshots will be added in a future update.

## Download

### Latest Release

**XUELi Watermark Tool v2.0.0**

[Download APK](https://github.com/Eason206/XUELiWatermarkTool/releases/tag/v2.0)

Android 8.0 (API 26) or later is required.

## Installation

1. Download the latest APK from [Releases](https://github.com/Eason206/XUELiWatermarkTool/releases).
2. Install the APK on your Android device.
3. Grant the **Display over other apps** permission.
4. Open XUELi Watermark Tool and configure your watermark.
5. Start the watermark overlay.

## Technical Details

* **Language:** Kotlin
* **UI:** Jetpack Compose
* **UI Library:** Miuix
* **Graphics / Effects:** AndroidLiquidGlass / Backdrop
* **Minimum SDK:** Android 8.0 (API 26)
* **Target SDK:** Android 15 (API 35)
* **Compile SDK:** Android 16 (API 37)
* **Gradle:** 9.6.1
* **Kotlin:** 2.3.20

The watermark overlay uses `TYPE_APPLICATION_OVERLAY` and `FLAG_NOT_TOUCHABLE`, allowing the watermark to remain visible without intercepting touch events from the application underneath.

Watermark settings are stored locally using `SharedPreferences`.

## Version History

### v2.0.0

* Completely redesigned UI
* New application icon
* Liquid Glass-inspired interactions
* Migrated to Jetpack Compose
* Migrated to Miuix UI
* Improved overall visual experience

### v1.0

* Initial release
* Original UI
* Custom two-line watermark
* Adjustable font size, opacity and spacing
* Floating overlay support

## License

This project is for personal and educational use.

---

<div align="center">

**XUELi Watermark Tool**

Made by [Eason206](https://github.com/Eason206)

</div>
