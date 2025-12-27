package com.example.priomatrix.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.priomatrix.DragState

@Composable
fun DragOverlay(dragState: DragState) {
    val task = dragState.task ?: return
    if (!dragState.isDragging) return

    val actualItemSize = 80.dp

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    (dragState.position.x - actualItemSize.toPx()).toInt(),
                    (dragState.position.y - actualItemSize.toPx()).toInt()
                )
            }
            .size(actualItemSize)
            .background(task.priority.color, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = task.id.toString(),
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}