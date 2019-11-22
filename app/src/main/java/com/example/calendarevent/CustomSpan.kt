package com.example.calendarevent

import android.graphics.Canvas
import android.graphics.Paint
import com.prolificinteractive.materialcalendarview.spans.DotSpan


class CustomSpan(private val color: Int, private val xOffset: Int) : DotSpan() {
    private val radius = 3f

    override fun drawBackground(
        canvas: Canvas, paint: Paint, left: Int, right: Int, top: Int, baseline: Int,
        bottom: Int, text: CharSequence?, start: Int, end: Int, lnum: Int
    ) {
        val oldColor: Int = paint.color

        if (color != 0) { paint.color = color }

        val x = (left + right) / 2

        canvas.drawCircle((x + xOffset).toFloat(), bottom + radius, radius, paint)

        paint.color = oldColor
    }
}