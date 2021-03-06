package com.example.sudokuWizard.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class BoardOverlayView(context : Context,
                       attributeSet: AttributeSet) : View(context, attributeSet) {

    private var cellPixelSize = 0F

    private var rows = 9
    private var cols = 9
    private var rowSubSize = 3
    private var colSubSize = 3

    /**** PAINT TYPES *****************************************************/
    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 10F
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2F
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Set height and width of the SudokuBoardView to be the min of the two dimensions
        val size = min(widthMeasureSpec - 64, heightMeasureSpec - 64)
        setMeasuredDimension(size, size)

        /** Adjust this later to account for varying sized boards. **/
    }

    override fun onDraw(canvas : Canvas) {
        cellPixelSize = (width / rows).toFloat()

        drawFrame(canvas)
    }

    private fun drawFrame(canvas: Canvas) {
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), thickLinePaint)

        for(i in 1 until rows) {
            val lineThickness = when (i % rowSubSize) {
                0 -> thickLinePaint
                else -> thinLinePaint
            }

            canvas.drawLine(
                    i * cellPixelSize,
                    0F,
                    i * cellPixelSize,
                    height.toFloat(),
                    lineThickness
            )

            canvas.drawLine(
                    0F,
                    i * cellPixelSize,
                    width.toFloat(),
                    i*cellPixelSize,
                    lineThickness
            )
        }
    }
}