package com.example.priomatrix.ui

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    onItemClicked: (Int) -> Unit,
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
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
    ) {

        /* ---------- TOP BAR ---------- */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(priority.color.copy(alpha = 0.25f))
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = priority.name,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF212121),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Back",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF546E7A),
                modifier = Modifier.clickable { onBack() }
            )
        }

        /* ---------- TASK LIST ---------- */
        if (tasks.isEmpty()) {
            EmptyTaskListView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp))
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    TaskItem(
                        task = task,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(82.dp),
                        borderColor = task.status.toIndicatorColor(),
                        onItemClicked = onItemClicked
                    )
                }
            }
        }

        /* ---------- BOTTOM QUICK ACTION BAR ---------- */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionCard(
                action = QuickActionItem.SortBy,
                description = sortOption.title,
                accent = priority.color,
                modifier = Modifier.weight(1f)
            ) {
                taskViewModel.setSortPopupStateOpen()
            }

            QuickActionCard(
                action = QuickActionItem.FilterBy,
                description = "${completionFilter.title} | ${nameFilter.title}",
                accent = priority.color,
                modifier = Modifier.weight(1f)
            ) {
                taskViewModel.setFilterPopupStateOpen()
            }
        }
    }

    /* ---------- POPUPS ---------- */
    if (isSortPopupOpen) {
        SortByPopup(
            selectedOption = sortOption,
            onOptionSelected = { taskViewModel.setSortOption(it) },
            onDismiss = { taskViewModel.setSortPopupStateClose() }
        )
    }

    if (isFilterPopupOpen) {
        FilterPopUp(
            selectedCompletion = completionFilter,
            selectedName = nameFilter,
            onCompletionSelected = { taskViewModel.setCompletionFilter(it) },
            onNameSelected = { taskViewModel.setNameFilter(it) },
            onDismiss = { taskViewModel.setFilterPopupStateClose() }
        )
    }
}

@Composable
fun EmptyTaskListView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🗂️",
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = "No tasks here",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF616161)
        )
        Text(
            text = "All caught up! 🙌",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF9E9E9E)
        )
    }
}

@Composable
fun QuickActionCard(
    action: QuickActionItem,
    description: String = "",
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(action.icon),
            contentDescription = null,
            tint = accent
        )

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = action.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF212121)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF757575)
            )
        }
    }
}
