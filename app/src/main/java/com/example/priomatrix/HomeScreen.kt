package com.example.priomatrix

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.priomatrix.ui.DragOverlay

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    var dragState by remember { mutableStateOf(DragState()) }

    var backlogTasks by remember { mutableStateOf(list) }   // PRIORITY_NONE
    var matrixTasks by remember {
        mutableStateOf(
            mapOf<Priority, List<Task>>(
                PRIORITY_ONE to emptyList(),
                PRIORITY_TWO to emptyList(),
                PRIORITY_THREE to emptyList(),
                PRIORITY_FOUR to emptyList()
            )
        )
    }

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
                    dragState.task?.let { dragged ->
                        // 1️⃣ Add task to matrix
                        matrixTasks = matrixTasks.toMutableMap().apply {
                            val updated = (this[priority] ?: emptyList()) +
                                    dragged.copy(priority = priority)
                            this[priority] = updated
                        }

                        // 2️⃣ Remove task from bottom list
                        backlogTasks = backlogTasks.filterNot { it.id == dragged.id }
                    }
                    dragState = DragState()
                },
                onTaskRollback = { task ->
                    // 🔥 ROLLBACK
                    // remove from matrix
                    matrixTasks = matrixTasks.toMutableMap().apply {
                        this[task.priority] = (this[task.priority] ?: emptyList()).filterNot { it.id == task.id }
                    }

                    // add back to backlog
                    backlogTasks = backlogTasks + task.copy(priority = PRIORITY_NONE)
                }
            )

            TaskListView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                tasks = backlogTasks,
                onDragStart = { task, offset ->
                    dragState = DragState(task, offset, true)
                },
                onDrag = { offset ->
                    dragState = dragState.copy(position = offset)
                },
                onDragEnd = {
                    dragState = dragState.copy(isDragging = false)
                }
            )
        }
        DragOverlay(dragState)
    }
}