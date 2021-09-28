package com.e.mbulak.utils

import android.graphics.*
import android.graphics.drawable.Drawable

class TextDrawable(private val text: String) : Drawable() {
    private val paint: Paint
    override fun draw(canvas: Canvas) {
        canvas.drawText(text, 0f, 15f, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        paint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    init {
        paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 39f
        paint.isAntiAlias = true
        paint.textAlign = Paint.Align.LEFT
    }
}