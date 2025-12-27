package com.example.priomatrix

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TaskListView(
    modifier: Modifier = Modifier,
    tasks : List<Task>,
    onDragStart: (Task, Offset) -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(
            items = tasks,
            key = { _, task -> task.id }
        ) { index, task ->
            var itemRootOffset by remember { mutableStateOf(Offset.Zero) }
            TaskItem(
                task = task,
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(width = 2.dp, Color.Gray, RoundedCornerShape(16.dp))
                    .padding(16.dp)
                    .pointerInput(task) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = { localOffset ->
                                // localOffset is the touch point inside the item
                                val startPosition = itemRootOffset + localOffset
                                onDragStart(task, startPosition)
                            },
                            onDrag = { change, _ ->
                                change.consume()
                                val position = itemRootOffset + change.position
                                onDrag(position)
                            },
                            onDragEnd = onDragEnd,
                            onDragCancel = onDragEnd
                        )
                    },
                onPositioned = { itemRootOffset = it }
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier,
    onPositioned: (Offset) -> Unit = {}
) {
    Row(
        modifier = modifier
            .onGloballyPositioned { coords ->
                onPositioned(coords.positionInRoot())
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(task.priority.color),
            contentAlignment = Alignment.Center,
        ){
            if(task.isCompleted){
                Text(
                    text = "✔",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "${task.id} : ${task.title}",
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}