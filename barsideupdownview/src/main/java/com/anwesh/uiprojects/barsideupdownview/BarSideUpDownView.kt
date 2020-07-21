package com.anwesh.uiprojects.barsideupdownview

/**
 * Created by anweshmishra on 21/07/20.
 */

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.content.Context
import android.app.Activity

val colors : Array<String> = arrayOf("#3F51B5", "#009688", "#F44336", "#2196F3", "#FF9800")
val parts : Int = 2
val scGap : Float = 0.02f / parts
val sizeFactor : Float = 8.9f
val delay : Long = 90
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawUpDownSideBar(i : Int, scale : Float, w : Float, h : Float, paint : Paint) {
    val barSize : Float = Math.min(w, h) / sizeFactor
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    save()
    scale(1f - 2 * i, 1f - 2 * i)
    translate(0f, h / 2 - barSize)
    drawRect(RectF(0f, 0f, (w / 2 - barSize) * sf1, barSize), paint)
    translate(w / 2 - barSize, 0f)
    drawRect(RectF(0f, -(h / 2 - barSize) * sf2, barSize, 0f), paint)
    restore()
}

fun Canvas.drawUpDownSideBars(scale : Float, w : Float, h : Float, paint : Paint) {
    for (j in 0..1) {
        drawUpDownSideBar(j, scale, w, h, paint)
    }
}

fun Canvas.drawUDSBNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = Color.parseColor(colors[i])
    save()
    translate(w / 2, h / 2)
    drawUpDownSideBars(scale, w, h, paint)
    restore()
}

class BarSideUpDownView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(delay)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }
}