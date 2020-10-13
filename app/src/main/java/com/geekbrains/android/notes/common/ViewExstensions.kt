package com.geekbrains.android.notes.common

import android.view.View

fun View.dip(value: Int): Int = (value * (resources?.displayMetrics?.density ?: 0f)).toInt()
fun View.dip(value: Float): Int = (value * (resources?.displayMetrics?.density ?: 0f)).toInt()