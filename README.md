# WatermarkOverlay

Android 14/15 适用的 Kotlin 悬浮水印示例。请用 Android Studio 打开本目录并同步 Gradle；首次启动后在系统页面授予“显示在其他应用上层”权限，再点击“启动水印”。

水印使用 `TYPE_APPLICATION_OVERLAY` 和 `FLAG_NOT_TOUCHABLE`，不会处理或拦截下方应用的触摸事件。参数保存在 `SharedPreferences` 中，修改后的参数在下次启动水印时生效。
