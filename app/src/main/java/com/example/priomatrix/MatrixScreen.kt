package com.example.priomatrix

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MatrixScreen(
    modifier: Modifier = Modifier,
    dragPosition : Offset,
    isDragging : Boolean,
    matrixTasks: Map<Priority, List<Task>>,
    onDrop: (Priority) -> Unit = {},
    onTaskRollback: (Task) -> Unit
) {
    val boundsMap = remember { mutableStateMapOf<Priority, Rect>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top row
        Row(modifier = Modifier.weight(1f)) {
            MatrixCell(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(2.dp, Color.Black),
                priority = PRIORITY_ONE,
                tasks = matrixTasks[PRIORITY_ONE].orEmpty(),
                onBoundsReady = { boundsMap[it.first] = it.second },
                onTaskRollback = onTaskRollback
            )
            MatrixCell(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(2.dp, Color.Black),
                priority = PRIORITY_TWO,
                tasks = matrixTasks[PRIORITY_TWO].orEmpty(),
                onBoundsReady = { boundsMap[it.first] = it.second },
                onTaskRollback = onTaskRollback
            )
        }

        // Bottom row
        Row(modifier = Modifier.weight(1f)) {
            MatrixCell(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(2.dp, Color.Black),
                priority = PRIORITY_THREE,
                tasks = matrixTasks[PRIORITY_THREE].orEmpty(),
                onBoundsReady = { boundsMap[it.first] = it.second },
                onTaskRollback = onTaskRollback
            )

            MatrixCell(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(2.dp, Color.Black),
                priority = PRIORITY_FOUR,
                tasks = matrixTasks[PRIORITY_FOUR].orEmpty(),
                onBoundsReady = { boundsMap[it.first] = it.second },
                onTaskRollback = onTaskRollback
            )
        }
    }

    // 🔥 DROP LOGIC LIVES HERE
    LaunchedEffect(isDragging) {
        if (!isDragging) {
            boundsMap.forEach { (priority, rect) ->
                if (rect.contains(dragPosition)) {
                    onDrop(priority)
                    return@LaunchedEffect
                }
            }
        }
    }
}


@Composable
fun MatrixCell(
    priority: Priority,
    tasks: List<Task>,
    modifier: Modifier,
    onBoundsReady: (Pair<Priority, Rect>) -> Unit,
    onTaskRollback: (Task) -> Unit
) {
    Box(
        modifier = modifier
            .onGloballyPositioned { coords ->
                onBoundsReady(priority to coords.boundsInRoot())
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f)
                .rotate(-45f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = priority.name,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
        LazyColumn(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = tasks,
                key = { _, task -> task.id }
            ) { index, task ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(task.priority.color.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .combinedClickable(
                            onClick = {},
                            onDoubleClick = {
                                onTaskRollback(task)   // 🔥
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(task.id.toString(), color = Color.White)
                }
            }
        }
    }
}