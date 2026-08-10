package com.example.watermarkoverlay

import android.app.*
import android.content.*
import android.graphics.PixelFormat
import android.os.IBinder
import android.provider.Settings
import android.view.*
import androidx.core.app.NotificationCompat

class WatermarkService : Service() {
    private lateinit var wm: WindowManager
    private var view: WatermarkView? = null
    override fun onCreate() { super.onCreate(); (getSystemService(NotificationManager::class.java)).createNotificationChannel(NotificationChannel(CHANNEL,"悬浮水印",NotificationManager.IMPORTANCE_LOW)) }
    override fun onStartCommand(intent: Intent?, flags: Int, id: Int): Int {
        if(!Settings.canDrawOverlays(this)){stopSelf();return START_NOT_STICKY}
        startForeground(NOTIFICATION, NotificationCompat.Builder(this,CHANNEL).setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle("悬浮水印正在运行").setContentText("在应用中可停止或修改设置").setOngoing(true).build())
        if(view==null){ wm=getSystemService(Context.WINDOW_SERVICE) as WindowManager; view=WatermarkView(this,SettingsStore.load(this)); val p=WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,PixelFormat.TRANSLUCENT).apply{gravity=Gravity.TOP or Gravity.START}; wm.addView(view,p); isRunning=true }
        return START_STICKY
    }
    override fun onDestroy(){view?.let{wm.removeView(it)};view=null;isRunning=false;super.onDestroy()}
    override fun onBind(intent: Intent?): IBinder?=null
    companion object { private const val CHANNEL="watermark_overlay"; private const val NOTIFICATION=1001; @Volatile var isRunning=false; private set }
}
