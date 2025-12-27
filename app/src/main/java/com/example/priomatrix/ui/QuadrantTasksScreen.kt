package com.example.priomatrix.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.priomatrix.Priority
import com.example.priomatrix.TaskItem
import com.example.priomatrix.TaskViewModel

@Composable
fun QuadrantTasksScreen(
    priorityId: Int,
    taskViewModel: TaskViewModel,
    onBack: () -> Unit
) {
    val matrixTasks by taskViewModel.matrixTasks.collectAsState()
    val priority = Priority.fromId(priorityId)
    val tasks = matrixTasks[priority] ?: emptyList()

    Column(modifier = Modifier.fillMaxSize()) {

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

        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                TaskItem(
                    task = task,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                )
            }
        }
    }
}
