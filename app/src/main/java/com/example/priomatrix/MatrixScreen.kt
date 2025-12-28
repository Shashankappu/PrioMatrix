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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.priomatrix.ui.theme.CellBorderColor
import com.example.priomatrix.ui.theme.PriorityFourBg
import com.example.priomatrix.ui.theme.PriorityOneBg
import com.example.priomatrix.ui.theme.PriorityThreeBg
import com.example.priomatrix.ui.theme.PriorityTwoBg
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
                onTaskRollback = onTaskRollback,
                onQuadrantClick = {
                    onQuadrantClick(PRIORITY_ONE)
                }
            )
            MatrixCell(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(2.dp, Color.Black),
                priority = PRIORITY_TWO,
                tasks = matrixTasks[PRIORITY_TWO].orEmpty(),
                onBoundsReady = { boundsMap[it.first] = it.second },
                onTaskRollback = onTaskRollback,
                onQuadrantClick = {
                    onQuadrantClick(PRIORITY_TWO)
                }
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
                onTaskRollback = onTaskRollback,
                onQuadrantClick = {
                    onQuadrantClick(PRIORITY_THREE)
                }
            )

            MatrixCell(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(2.dp, Color.Black),
                priority = PRIORITY_FOUR,
                tasks = matrixTasks[PRIORITY_FOUR].orEmpty(),
                onBoundsReady = { boundsMap[it.first] = it.second },
                onTaskRollback = onTaskRollback,
                onQuadrantClick = {
                    onQuadrantClick(PRIORITY_FOUR)
                }
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
    onTaskRollback: (Task) -> Unit,
    onQuadrantClick: () -> Unit
) {
    val bgColor = when (priority) {
        PRIORITY_ONE -> PriorityOneBg
        PRIORITY_TWO -> PriorityTwoBg
        PRIORITY_THREE -> PriorityThreeBg
        PRIORITY_FOUR -> PriorityFourBg
        PRIORITY_NONE -> Color.Gray
        else -> {Color.Gray}
    }

    Box(
        modifier = modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .border(
                1.5.dp,
                Color.Black.copy(alpha = 0.15f),
                RoundedCornerShape(20.dp)
            )
            .onGloballyPositioned { coords ->
                onBoundsReady(priority to coords.boundsInRoot())
            }
    ) {

        // 🔹 Corner label
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White.copy(alpha = 0.9f))
                .clickable { onQuadrantClick() }
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = priority.name,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )
        }

        // 🔹 Task list
        LazyColumn(
            modifier = Modifier
                .padding(top = 44.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = tasks,
                key = { _, task -> task.id }
            ) { _, task ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(
                            1.dp,
                            Color.Black.copy(alpha = 0.08f),
                            RoundedCornerShape(12.dp)
                        )
                        .combinedClickable(
                            onClick = { onQuadrantClick() },
                            onDoubleClick = { onTaskRollback(task) }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = task.id.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
