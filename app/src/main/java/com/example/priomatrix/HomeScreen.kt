package com.example.priomatrix

import android.widget.AdapterView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.priomatrix.ui.DragOverlay
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    onQuadrantClick: (Priority) -> Unit,
    taskViewModel: TaskViewModel,
    modifier: Modifier = Modifier,
    onItemClicked : (Int) -> Unit,
    onAddTaskClick: () -> Unit = {},
    onImportCsv: () -> Unit = {},
    onImportExcel: () -> Unit = {},
    onUploadFile: () -> Unit = {}
) {
    val backlogTasks by taskViewModel.backlogTasks.collectAsState(emptyList())
    val matrixTasks by taskViewModel.matrixTasks.collectAsState()
    val dragState by taskViewModel.dragState.collectAsState()

    var showEmptyBacklog by remember { mutableStateOf(true) }

    LaunchedEffect(backlogTasks.isEmpty()) {
        if (backlogTasks.isEmpty()) {
            showEmptyBacklog = true
            delay(2500) // 2.5 seconds
            showEmptyBacklog = false
        }
    }

    val matrixHeightFraction by animateFloatAsState(
        targetValue = if (showEmptyBacklog || backlogTasks.isNotEmpty()) 0.55f else 0.85f,
        label = "matrix-height"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
    ) {
        var isFabMenuOpen by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /* ---------- HEADER ---------- */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
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

            /* ---------- MATRIX ---------- */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(matrixHeightFraction)
                    .padding(horizontal = 12.dp)
                    .background(
                        Color(0xFF2B2B2B),
                        RoundedCornerShape(22.dp)
                    )
                    .padding(12.dp)
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

            /* ---------- BACKLOG ---------- */
            AnimatedVisibility(
                visible = showEmptyBacklog || backlogTasks.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
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
                        // EMPTY STATE (temporary)
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(0.7f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🗂️", style = MaterialTheme.typography.displayMedium)
                            Text(
                                "No pending tasks",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF616161)
                            )
                            Text(
                                "You're all caught up 👌",
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
                            },
                            onItemClicked = onItemClicked
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(20.dp)
            ) {

                DropdownMenu(
                    expanded = isFabMenuOpen,
                    onDismissRequest = { isFabMenuOpen = false },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Color.White,
                            RoundedCornerShape(16.dp)
                        )

                ) {
                    FabMenuItem("Add Manually") {
                        isFabMenuOpen = false
                        // TODO: navigate to AddTask screen
                    }
                    FabMenuItem("Import CSV") {
                        isFabMenuOpen = false
                        // TODO: CSV import
                    }
                    FabMenuItem("Import Excel") {
                        isFabMenuOpen = false
                        // TODO: Excel import
                    }
                    FabMenuItem("Upload File") {
                        isFabMenuOpen = false
                        // TODO: File picker
                    }
                }

                FloatingActionButton(
                    onClick = { isFabMenuOpen = !isFabMenuOpen },
                    containerColor = Color(0xFF212121),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text(
                        text = "+",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                }
            }
        }
        DragOverlay(dragState)
    }
}


@Composable
private fun EmptyBacklogState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.8f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🗂️", style = MaterialTheme.typography.displayMedium)
        Text(
            text = "No pending tasks",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF616161)
        )
        Text(
            text = "Import tasks to get started",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF9E9E9E)
        )
    }
}

@Composable
private fun FabMenuItem(
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        style = MaterialTheme.typography.bodyMedium,
        color = Color(0xFF212121)
    )
}