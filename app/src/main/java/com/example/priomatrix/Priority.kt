package com.example.priomatrix

import androidx.compose.ui.graphics.Color

data class Priority(
    val id: Int,
    val name: String,
    val color: Color
){
    companion object {
        fun fromId(id: Int): Priority {
            return when (id) {
                1 -> PRIORITY_ONE
                2 -> PRIORITY_TWO
                3 -> PRIORITY_THREE
                4 -> PRIORITY_FOUR
                else -> PRIORITY_NONE
            }
        }
    }
}

val PRIORITY_NONE = Priority(
    0, "None", Color.White
)

val PRIORITY_ONE = Priority(
    1, "Urgent and Important", Color(0xFFC62828).copy(0.5f)
)

val PRIORITY_TWO = Priority(
    2, "Urgent but Not Important", Color(0xFFFF8F00).copy(0.5f)
)

val PRIORITY_THREE = Priority(
    3, "Not Urgent but Important", Color.Yellow.copy(0.5f)
)

val PRIORITY_FOUR = Priority(
    4, "Not Urgent and Not Important", Color.DarkGray.copy(0.5f)
)

