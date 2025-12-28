package com.example.priomatrix

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.priomatrix.ui.DragOverlay

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
            .fillMaxSize()
            .background(Color(0xFFFDFDFD)) // dull white background
            .padding(12.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /* ---------- HEADER ---------- */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "PrioMatrix",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF212121)
                    )
                    Text(
                        text = "Focus on what truly matters",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF616161)
                    )
                }
                Text(
                    text = "v1.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF9E9E9E)
                )
            }

            /* ---------- MATRIX SECTION (Centered Hero) ---------- */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.55f)
                    .padding(horizontal = 8.dp)
                    .background(
                        color = Color(0xC91C1C1C), // very dark gray,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                MatrixScreen(
                    modifier = Modifier.fillMaxSize(),
                    dragPosition = dragState.position,
                    isDragging = dragState.isDragging,
                    matrixTasks = matrixTasks,
                    onDrop = { taskViewModel.dropInto(it) },
                    onTaskRollback = { taskViewModel.rollbackTask(it) },
                    onQuadrantClick = onQuadrantClick
                )
            }

            /* ---------- BACKLOG SECTION ---------- */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        Color(0xFFF5F5F5),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Backlog",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF424242)
                )

                if (backlogTasks.isEmpty()) {
                    // Empty State
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(0.7f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🗂️",
                            style = MaterialTheme.typography.displayMedium
                        )
                        Text(
                            text = "No pending tasks",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF616161)
                        )
                        Text(
                            text = "You're all caught up 👌",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF9E9E9E)
                        )
                    }
                } else {
                    TaskListView(
                        modifier = Modifier.fillMaxSize(),
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
            }
        }

        /* ---------- DRAG OVERLAY ---------- */
        DragOverlay(dragState)
    }
}