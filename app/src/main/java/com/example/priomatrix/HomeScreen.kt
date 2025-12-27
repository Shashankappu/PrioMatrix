package com.example.priomatrix

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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