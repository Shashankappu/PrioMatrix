package com.example.priomatrix

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.priomatrix.ui.TaskStatus
import com.example.priomatrix.ui.indicatorColor
import com.example.priomatrix.ui.theme.TaskBgColor

@Composable
fun MatrixScreen(
    modifier: Modifier = Modifier,
    dragPosition : Offset,
    isDragging : Boolean,
    matrixTasks: Map<Priority, List<Task>>,
    onDrop: (Priority) -> Unit = {},
    onQuadrantClick: (Priority) -> Unit,
    onTaskRollback: (Task) -> Unit
) {
    val boundsMap = remember { mutableStateMapOf<Priority, Rect>() }

    // Dark container with rounded corners
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp)) // big corner radius for container
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Top row
                Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MatrixCell(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        priority = PRIORITY_ONE,
                        tasks = matrixTasks[PRIORITY_ONE].orEmpty(),
                        onBoundsReady = { boundsMap[it.first] = it.second },
                        onTaskRollback = onTaskRollback,
                        onQuadrantClick = { onQuadrantClick(PRIORITY_ONE) }
                    )
                    MatrixCell(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        priority = PRIORITY_TWO,
                        tasks = matrixTasks[PRIORITY_TWO].orEmpty(),
                        onBoundsReady = { boundsMap[it.first] = it.second },
                        onTaskRollback = onTaskRollback,
                        onQuadrantClick = { onQuadrantClick(PRIORITY_TWO) }
                    )
                }

                // Bottom row
                Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MatrixCell(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        priority = PRIORITY_THREE,
                        tasks = matrixTasks[PRIORITY_THREE].orEmpty(),
                        onBoundsReady = { boundsMap[it.first] = it.second },
                        onTaskRollback = onTaskRollback,
                        onQuadrantClick = { onQuadrantClick(PRIORITY_THREE) }
                    )
                    MatrixCell(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        priority = PRIORITY_FOUR,
                        tasks = matrixTasks[PRIORITY_FOUR].orEmpty(),
                        onBoundsReady = { boundsMap[it.first] = it.second },
                        onTaskRollback = onTaskRollback,
                        onQuadrantClick = { onQuadrantClick(PRIORITY_FOUR) }
                    )
                }
            }
        }

        // DROP LOGIC
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
}


@Composable
fun MatrixCell(
    priority: Priority,
    tasks: List<Task>,
    modifier: Modifier,
    onBoundsReady: (Pair<Priority, Rect>) -> Unit,
    onTaskRollback: (Task) -> Unit,
    onQuadrantClick: () -> Unit
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(priority.toQuadrantBg())
            .onGloballyPositioned { coords ->
                onBoundsReady(priority to coords.boundsInRoot())
            }
    ) {
        // Corner label
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(6.dp)
                .clip(RoundedCornerShape(40))
                .background(Color.White.copy(alpha = 0.9f))
                .clickable { onQuadrantClick() }
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Text(
                text = priority.name,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF212121)
            )
        }

        // Task list
        LazyColumn(
            modifier = Modifier
                .padding(top = 36.dp, start = 6.dp, end = 6.dp, bottom = 6.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            itemsIndexed(
                items = tasks,
                key = { _, task -> task.id }
            ) { _, task ->
                MatrixTaskItem(
                    task = task,
                    onClick = { onQuadrantClick()},
                    onDoubleClick = { onTaskRollback(task) }
                )
            }
        }
    }
}


@Composable
private fun MatrixTaskItem(
    task: Task,
    onClick: () -> Unit,
    onDoubleClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFFFFBFA))
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(10.dp)
            )
            .combinedClickable(
                onClick = onClick,
                onDoubleClick = onDoubleClick
            )
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // Status dot
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(task.status.indicatorColor())
            )

            // Title
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF212121),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
