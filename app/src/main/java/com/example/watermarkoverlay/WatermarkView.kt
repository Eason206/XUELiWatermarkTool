package com.example.watermarkoverlay

import android.content.Context
import android.graphics.*
import android.util.TypedValue
import android.view.View

class WatermarkView(context: Context, private val s: WatermarkSettings): View(context) {
    private val density=resources.displayMetrics.density
    private val paint=Paint(Paint.ANTI_ALIAS_FLAG).apply { color=Color.GRAY; alpha=(255*s.opacityPercent/100f).toInt(); textSize=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,s.textSizeSp.toFloat(),resources.displayMetrics); typeface=Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL); textAlign=Paint.Align.CENTER }
    override fun onDraw(c:Canvas){
        val lineHeight=paint.fontMetrics.descent-paint.fontMetrics.ascent
        val requestedStep=lineHeight*2+s.spacingDp*density
        // Keep the grid evenly distributed across the full overlay. The configured gap
        // is still honored when it requires a greater separation than the screen grid.
        val rowStep=maxOf(height/4f,requestedStep)
        val firstCenterY=(height-rowStep*3)/2f
        val columns=floatArrayOf(width*.25f,width*.75f)
        repeat(4){ row ->
            val centerY=firstCenterY+row*rowStep
            columns.forEach { centerX -> drawWatermarkGroup(c,centerX,centerY,lineHeight) }
        }
    }
    private fun drawWatermarkGroup(c:Canvas,centerX:Float,centerY:Float,lineHeight:Float){
        c.save()
        c.translate(centerX,centerY)
        c.rotate(-30f)
        c.drawText(s.lineOne,0f,-lineHeight*.2f,paint)
        c.drawText(s.lineTwo,0f,lineHeight*.95f,paint)
        c.restore()
    }
}
