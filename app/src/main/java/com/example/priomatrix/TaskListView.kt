package com.example.priomatrix

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.priomatrix.ui.IconTint
import com.example.priomatrix.ui.Severity
import com.example.priomatrix.ui.indicatorIcon

@Composable
fun TaskListView(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onDragStart: (Task, Offset) -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    onItemClicked : (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 64.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(
            items = tasks,
            key = { _, task -> task.id }
        ) { _, task ->
            var itemRootOffset by remember { mutableStateOf(Offset.Zero) }
            TaskItem(
                task = task,
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .pointerInput(task) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = { localOffset ->
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
                onPositioned = { itemRootOffset = it },
                onItemClicked = onItemClicked
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit,
    onPositioned: (Offset) -> Unit = {}
) {
    Row(
        modifier = modifier
            .onGloballyPositioned { onPositioned(it.positionInRoot()) }
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFFFFBFA))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .clickable{
                onItemClicked(task.id)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        /* ---------- Severity ---------- */
        SeverityIcon(task.severity)

        Spacer(Modifier.width(12.dp))


        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            /* Title + Status */
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF212121),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFF0F0F0))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = task.owner.ifBlank { "Unassigned" },
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF616161)
                    )
                }
            }

            /* Description — FULL WIDTH, no cropping */
            if (task.description.isNotBlank()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF757575),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun SeverityIcon(severity: Severity) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(RoundedCornerShape(4.dp))
    ){
        Icon(
            painter = painterResource(severity.indicatorIcon()),
            contentDescription = "Severity Icon",
            tint = severity.IconTint()
        )
    }
}