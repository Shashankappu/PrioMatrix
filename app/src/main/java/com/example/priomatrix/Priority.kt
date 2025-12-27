package com.example.priomatrix

import androidx.compose.ui.graphics.Color

data class Priority(
    val id: Int,
    val name: String,
    val color: Color
)

val PRIORITY_NONE = Priority(
    0, "None", Color.Gray
)

val PRIORITY_ONE = Priority(
    1, "Urgent and Important", Color.Red
)
val PRIORITY_TWO = Priority(
    2, "Urgent but Not Important", Color.Yellow
)

val PRIORITY_THREE = Priority(
    3, "Not Urgent but Important", Color.Magenta
)

val PRIORITY_FOUR = Priority(
    4, "Not Urgent and Not Important", Color.White
)

