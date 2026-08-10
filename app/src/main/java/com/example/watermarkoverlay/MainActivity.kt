package com.example.watermarkoverlay

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var status: TextView; private lateinit var grant: Button; private lateinit var toggle: Button
    private lateinit var one: TextInputEditText; private lateinit var two: TextInputEditText
    private lateinit var size: SeekBar; private lateinit var alpha: SeekBar; private lateinit var gap: SeekBar
    private lateinit var sizeLabel: TextView; private lateinit var alphaLabel: TextView; private lateinit var gapLabel: TextView
    private val notificationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { }
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_main)
        status=findViewById(R.id.permissionStatus); grant=findViewById(R.id.permissionButton); toggle=findViewById(R.id.startStopButton)
        one=findViewById(R.id.lineOneInput); two=findViewById(R.id.lineTwoInput); size=findViewById(R.id.textSizeSeekBar); alpha=findViewById(R.id.opacitySeekBar); gap=findViewById(R.id.spacingSeekBar)
        sizeLabel=findViewById(R.id.textSizeLabel); alphaLabel=findViewById(R.id.opacityLabel); gapLabel=findViewById(R.id.spacingLabel)
        val s=SettingsStore.load(this); one.setText(s.lineOne); two.setText(s.lineTwo); size.progress=s.textSizeSp-12; alpha.progress=s.opacityPercent-10; gap.progress=s.spacingDp-20
        listOf(size,alpha,gap).forEach { it.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener { override fun onProgressChanged(b:SeekBar,p:Int,f:Boolean)=labels(); override fun onStartTrackingTouch(b:SeekBar)=Unit; override fun onStopTrackingTouch(b:SeekBar)=Unit }) }; labels()
        grant.setOnClickListener { startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))) }
        toggle.setOnClickListener { if(WatermarkService.isRunning) stopService(Intent(this,WatermarkService::class.java)) else startOverlay() }
        if(Build.VERSION.SDK_INT>=33) notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
    override fun onResume(){super.onResume(); val ok=Settings.canDrawOverlays(this); status.text="悬浮窗权限："+if(ok)"已授予" else "未授予"; grant.visibility=if(ok) View.GONE else View.VISIBLE; toggle.text=if(WatermarkService.isRunning)"停止水印" else "启动水印"}
    override fun onPause(){ SettingsStore.save(this, readSettings()); super.onPause() }
    private fun labels(){sizeLabel.text="字号：${size.progress+12} sp"; alphaLabel.text="透明度：${alpha.progress+10}%"; gapLabel.text="组间距：${gap.progress+20} dp"}
    private fun readSettings()=WatermarkSettings(one.text?.toString()?.ifBlank{"26847365912"}?:"26847365912",two.text?.toString()?.ifBlank{"864927051738426"}?:"864927051738426",size.progress+12,alpha.progress+10,gap.progress+20)
    private fun startOverlay(){if(!Settings.canDrawOverlays(this)){grant.performClick();return}; SettingsStore.save(this,readSettings()); val i=Intent(this,WatermarkService::class.java); if(Build.VERSION.SDK_INT>=26)startForegroundService(i) else startService(i); toggle.text="停止水印"}
}
