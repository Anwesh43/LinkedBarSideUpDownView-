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
