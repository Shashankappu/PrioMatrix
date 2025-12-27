package com.example.priomatrix

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.priomatrix.ui.DragOverlay
import kotlin.collections.emptyList

@Composable
fun HomeScreen(
    onQuadrantClick: (Priority) -> Unit,
    taskViewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {

    val backlogTasks by taskViewModel.backlogTasks.collectAsState(emptyList())
    val matrixTasks by taskViewModel.matrixTasks.collectAsState()
    val dragState by taskViewModel.dragState.collectAsState()

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PrioMatrix",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Version 1.0",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            MatrixScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                dragPosition = dragState.position,
                isDragging = dragState.isDragging,
                matrixTasks = matrixTasks,
                onDrop = { priority ->
                    taskViewModel.dropInto(priority)
                },
                onTaskRollback = { task ->
                    taskViewModel.rollbackTask(task)
                },
                onQuadrantClick = onQuadrantClick
            )

            TaskListView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                tasks = backlogTasks,
                onDragStart = { task, offset ->
                    taskViewModel.startDrag(task, offset)
                },
                onDrag = { offset ->
                    taskViewModel.updateDrag(offset)
                },
                onDragEnd = {
                    taskViewModel.endDrag()
                }
            )
        }
        DragOverlay(dragState)
    }
}