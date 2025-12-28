package com.example.priomatrix.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.priomatrix.Priority
import com.example.priomatrix.TaskItem
import com.example.priomatrix.TaskViewModel

@Composable
fun PriorityTaskListScreen(
    priorityId: Int,
    taskViewModel: TaskViewModel,
    onBack: () -> Unit
) {
    val matrixTasks by taskViewModel.matrixTasks.collectAsState()
    val priority = Priority.fromId(priorityId)
    val isFilterPopupOpen by taskViewModel.isFilterPopupOpen.collectAsState()
    val isSortPopupOpen by taskViewModel.isSortPopupOpen.collectAsState()

    val completionFilter by taskViewModel.completionFilter.collectAsState()
    val nameFilter by taskViewModel.nameFilter.collectAsState()
    val sortOption by taskViewModel.sortOption.collectAsState()

    val tasks = taskViewModel.filteredAndSortedTasksForPriority(
        priority = priority,
        matrixTasks = matrixTasks,
        completion = completionFilter,
        nameFilter = nameFilter,
        sortOption = sortOption
    )


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = priority.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Back",
                modifier = Modifier.clickable { onBack() }
            )
        }
        // TaskListView
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                val borderColor = if(task.isCompleted) Color.Green else Color.Gray
                TaskItem(
                    task = task,
                    modifier =  Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(width = 2.dp, borderColor, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                )
            }
        }

        // Bottom QuickAction bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .background(Color.White)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            QuickActionItem(
                action = QuickActionItem.SortBy,
                description = sortOption.title,
                modifier = Modifier
                        . clickable {
                    taskViewModel.setSortPopupStateOpen()
                },
            )
            VerticalDivider(modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(Color.White))
            QuickActionItem(
                action = QuickActionItem.FilterBy,
                modifier = Modifier
                    .clickable {
                        taskViewModel.setFilterPopupStateOpen()
                    },
            )
        }
    }
    if (isSortPopupOpen) {
        SortByPopup(
            selectedOption = sortOption,
            onOptionSelected = {
                taskViewModel.setSortOption(it)
            },
            onDismiss = {
                taskViewModel.setSortPopupStateClose()
            }
        )
    }
    if (isFilterPopupOpen) {
        FilterPopUp(
            selectedCompletion = completionFilter,
            selectedName = nameFilter,
            onCompletionSelected = {
                taskViewModel.setCompletionFilter(it)
            },
            onNameSelected = {
                taskViewModel.setNameFilter(it)
            },
            onDismiss = {
                taskViewModel.setFilterPopupStateClose()
            }
        )
    }
}

@Composable
fun QuickActionItem(
    modifier: Modifier = Modifier,
    action: QuickActionItem,
    description: String = ""
) {
    Row(
        modifier = modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(action.icon),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.padding(end = 8.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = action.name,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
            if(description.isNotEmpty()) {
                Text(
                    text = description,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
