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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.priomatrix.PRIORITY_FOUR
import com.example.priomatrix.PRIORITY_ONE
import com.example.priomatrix.PRIORITY_THREE
import com.example.priomatrix.PRIORITY_TWO
import com.example.priomatrix.TaskItem
import com.example.priomatrix.list

@Composable
fun QuadrantTasksScreen(
    priorityId: Int,
    onBack: () -> Unit
) {
    val priority = remember(priorityId) {
        listOf(
            PRIORITY_ONE,
            PRIORITY_TWO,
            PRIORITY_THREE,
            PRIORITY_FOUR
        ).first { it.id == priorityId }
    }

    val tasks = remember {
        // Replace later with ViewModel
        list.filter { it.priority.id == priorityId }
    }

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
