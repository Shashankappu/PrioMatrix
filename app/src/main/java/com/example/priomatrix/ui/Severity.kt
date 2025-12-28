package com.example.priomatrix.ui

import androidx.compose.ui.graphics.Color
import com.example.priomatrix.R

enum class Severity(val id :Int) {
   CRITICAL(1),MAJOR(2),MODERATE(3),LOW(4)
}

fun Severity.indicatorIcon(): Int {
    return when (this) {
        Severity.CRITICAL -> R.drawable.vd_critical
        Severity.MAJOR -> R.drawable.vd_major
        Severity.MODERATE -> R.drawable.vd_moderate
        Severity.LOW -> R.drawable.vd_minor
    }
}

fun Severity.IconTint(): Color {
    return when (this) {
        Severity.CRITICAL -> Color.Red
        Severity.MAJOR -> Color.Red
        Severity.MODERATE -> Color.Blue
        Severity.LOW -> Color.Blue
    }
}