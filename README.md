<div align="center">

<img src="app/src/main/res/drawable/ic_launcher.png" width="128" alt="XUELi Watermark Tool">

# XUELi Watermark Tool

轻量级 Android 悬浮水印工具
A lightweight Android floating watermark tool.

[![Release](https://img.shields.io/github/v/release/Eason206/XUELiWatermarkTool?style=flat-square)](https://github.com/Eason206/XUELiWatermarkTool/releases)
[![Android](https://img.shields.io/badge/Android-8.0%2B-green?style=flat-square)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.20-7F52FF?style=flat-square)](https://kotlinlang.org/)

</div>

## 简介 · About

**XUELi Watermark Tool** 是一款轻量级 Android 悬浮水印工具，可以在屏幕上显示自定义的双行文字水印。

**XUELi Watermark Tool** is a lightweight Android floating watermark application that displays customizable two-line text overlays.

当前最新版本为 **2.0.0**。

The current version is **2.0.0**.

## 功能 · Features

* 自定义双行水印 · Custom two-line watermark
* 调整字体大小 · Adjustable font size
* 调整透明度 · Adjustable opacity
* 调整文字间距 · Adjustable spacing
* 悬浮窗显示 · Floating overlay
* 不拦截触摸操作 · Non-interactive overlay
* 全新 Compose UI · Modern Jetpack Compose UI
* Liquid Glass 风格交互 · Liquid Glass-inspired interactions

## 界面 · Screenshots

> 界面截图将在后续版本中加入。
> Screenshots will be added in a future update.

## 下载 · Download

### 最新版本 · Latest Release

**XUELi Watermark Tool v2.0.0**

[下载 APK · Download APK](https://github.com/Eason206/XUELiWatermarkTool/releases/tag/v2.0)

需要 Android 8.0（API 26）或更高版本。

Android 8.0 (API 26) or later is required.

## 安装 · Installation

1. 从 [Releases](https://github.com/Eason206/XUELiWatermarkTool/releases) 下载最新 APK。
   Download the latest APK from Releases.

2. 安装 APK。
   Install the APK on your Android device.

3. 授予“显示在其他应用上层”权限。
   Grant the **Display over other apps** permission.

4. 打开 XUELi Watermark Tool 并设置水印。
   Open XUELi Watermark Tool and configure the watermark.

5. 启动悬浮水印。
   Start the watermark overlay.

## 技术信息 · Technical Details

| 项目                 | 信息                            |
| ------------------ | ----------------------------- |
| 开发语言 · Language    | Kotlin                        |
| UI 框架 · UI         | Jetpack Compose               |
| UI 库 · UI Library  | Miuix                         |
| 图形效果 · Graphics    | AndroidLiquidGlass / Backdrop |
| 最低版本 · Minimum SDK | Android 8.0 (API 26)          |
| 目标版本 · Target SDK  | Android 15 (API 35)           |
| 编译版本 · Compile SDK | Android 16 (API 37)           |
| Kotlin             | 2.3.20                        |
| Gradle             | 9.6.1                         |

水印悬浮层使用 `TYPE_APPLICATION_OVERLAY` 和 `FLAG_NOT_TOUCHABLE`，因此不会拦截下方应用的触摸操作。

The watermark overlay uses `TYPE_APPLICATION_OVERLAY` and `FLAG_NOT_TOUCHABLE`, so it does not intercept touch events from the application underneath.

水印设置使用 `SharedPreferences` 保存在本地。

Watermark settings are stored locally using `SharedPreferences`.

## 更新历史 · Version History

### v2.0.0

* 全新 UI 设计 · Completely redesigned UI
* 全新应用图标 · New application icon
* Liquid Glass 风格交互 · Liquid Glass-inspired interactions
* 迁移至 Jetpack Compose · Migrated to Jetpack Compose
* 迁移至 Miuix UI · Migrated to Miuix UI
* 全面优化视觉体验 · Improved overall visual experience

### v1.0

* 首个公开版本 · Initial public release
* 原版 UI · Original UI
* 自定义双行水印 · Custom two-line watermark
* 可调字体大小、透明度和间距 · Adjustable font size, opacity and spacing
* 悬浮窗支持 · Floating overlay support

## 开源 · Open Source

本项目使用 Kotlin 开发，并以学习、研究和个人使用为主要目的。

This project is developed with Kotlin and is primarily intended for learning, research, and personal use.

---
## 致谢 · Credits

特别感谢以下开源项目与作者：

### Miuix

感谢 **Miuix** 作者及贡献者，为本项目提供了优秀的 Android UI 组件与设计基础，使 XUELi Watermark Tool 能够拥有更加现代化的界面体验。

Special thanks to the **Miuix** authors and contributors for providing an excellent Android UI framework and design foundation.

### Kyant0 / AndroidLiquidGlass

特别感谢 **Kyant0**，以及其 **AndroidLiquidGlass / Backdrop** 项目。

本项目的 Liquid Glass 风格效果与部分交互实现参考并使用了相关开源技术。

Special thanks to **Kyant0** and the **AndroidLiquidGlass / Backdrop** project, which provided the foundation and inspiration for the Liquid Glass visual effects and interactions used in this project.

感谢所有开源项目的作者与贡献者。
Thanks to all open-source authors and contributors who make projects like this possible.


<div align="center">

**XUELi Watermark Tool**

由 [Eason206](https://github.com/Eason206) 制作
Made by [Eason206](https://github.com/Eason206)

</div>
