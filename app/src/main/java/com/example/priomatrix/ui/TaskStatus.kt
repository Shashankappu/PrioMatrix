package com.example.priomatrix.ui

import androidx.compose.ui.graphics.Color
import com.example.priomatrix.ui.theme.TaskStatusCompletedColor
import com.example.priomatrix.ui.theme.TaskStatusInProgressColor
import com.example.priomatrix.ui.theme.TaskStatusPendingColor

enum class TaskStatus {
    PENDING,        // Task not started yet
    IN_PROGRESS,    // Task currently in progress
    COMPLETED       // Task finished
}

fun TaskStatus.indicatorColor(): Color {
    return when (this) {
        TaskStatus.PENDING -> TaskStatusPendingColor
        TaskStatus.IN_PROGRESS -> TaskStatusInProgressColor
        TaskStatus.COMPLETED -> TaskStatusCompletedColor
    }
}