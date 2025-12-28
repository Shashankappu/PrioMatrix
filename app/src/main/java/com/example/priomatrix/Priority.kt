package com.example.priomatrix

import androidx.compose.ui.graphics.Color
import com.example.priomatrix.ui.theme.PriorityFourBg
import com.example.priomatrix.ui.theme.PriorityNoneBg
import com.example.priomatrix.ui.theme.PriorityOneBg
import com.example.priomatrix.ui.theme.PriorityThreeBg
import com.example.priomatrix.ui.theme.PriorityTwoBg

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
    id = 0,
    name = "Unassigned",
    color = PriorityNoneBg // or existing gray mapping
)

val PRIORITY_ONE = Priority(
    id = 1,
    name = "Do Now",
    color = PriorityOneBg
)

val PRIORITY_TWO = Priority(
    id = 2,
    name = "Schedule",
    color = PriorityTwoBg
)

val PRIORITY_THREE = Priority(
    id = 3,
    name = "Delegate",
    color = PriorityThreeBg
)

val PRIORITY_FOUR = Priority(
    id = 4,
    name = "Eliminate",
    color = PriorityFourBg
)

