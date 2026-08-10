package com.example.watermarkoverlay

import android.content.Context
import android.graphics.*
import android.util.TypedValue
import android.view.View

class WatermarkView(context: Context, private val s: WatermarkSettings): View(context) {
    private val density=resources.displayMetrics.density
    private val paint=Paint(Paint.ANTI_ALIAS_FLAG).apply { color=Color.GRAY; alpha=(255*s.opacityPercent/100f).toInt(); textSize=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,s.textSizeSp.toFloat(),resources.displayMetrics); typeface=Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL) }
    override fun onDraw(c:Canvas){ val h=paint.fontMetrics.descent-paint.fontMetrics.ascent; val step=h*2+s.spacingDp*density; val firstY=(height-(step*3+h*1.15f))/2f; c.save(); c.rotate(-30f,width/2f,height/2f); repeat(4){ row -> val y=firstY+row*step; for(x in floatArrayOf(28f*density,width/2f+28f*density)){c.drawText(s.lineOne,x,y,paint);c.drawText(s.lineTwo,x,y+h*1.15f,paint)} };c.restore() }
}
