package com.example.priomatrix

import androidx.compose.ui.geometry.Offset

data class DragState(
    val task: Task? = null,
    val position: Offset = Offset.Zero,
    val isDragging: Boolean = false
)