package com.example.priomatrix

import android.view.animation.GridLayoutAnimationController.PRIORITY_NONE
import androidx.compose.ui.graphics.Color
import com.example.priomatrix.ui.theme.PriorityFourBg
import com.example.priomatrix.ui.theme.PriorityNoneBg
import com.example.priomatrix.ui.theme.PriorityOneBg
import com.example.priomatrix.ui.theme.PriorityThreeBg
import com.example.priomatrix.ui.theme.PriorityTwoBg
import com.example.priomatrix.ui.theme.QuadrantFourBg
import com.example.priomatrix.ui.theme.QuadrantOneBg
import com.example.priomatrix.ui.theme.QuadrantThreeBg
import com.example.priomatrix.ui.theme.QuadrantTwoBg

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
            } as Priority
        }
    }
}

fun Priority.toQuadrantBg() : Color {
    return when (this) {
        PRIORITY_ONE -> QuadrantOneBg
        PRIORITY_TWO -> QuadrantTwoBg
        PRIORITY_THREE -> QuadrantThreeBg
        PRIORITY_FOUR -> QuadrantFourBg
        else -> Color.Gray
    }
}


val PRIORITY_NONE = Priority(
    id = 0,
    name = "Unassigned",
    color = PriorityNoneBg
)

val PRIORITY_ONE = Priority(
    id = 1,
    name = "Urgent & Important",
    color = PriorityOneBg
)

val PRIORITY_TWO = Priority(
    id = 2,
    name = "Urgent, Not Important",
    color = PriorityTwoBg
)

val PRIORITY_THREE = Priority(
    id = 3,
    name = "Important, Not Urgent",
    color = PriorityThreeBg
)

val PRIORITY_FOUR = Priority(
    id = 4,
    name = "Low Importance",
    color = PriorityFourBg
)

