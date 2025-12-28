package com.example.priomatrix.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.priomatrix.Task

@Composable
fun TaskDetailsScreen(
    task: Task,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
            .padding(16.dp)
    ) {
        /* ---------- Top Bar ---------- */
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Task Details",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Back",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF546E7A),
                modifier = Modifier
                    .clickable { onBack() }
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        /* ---------- Priority & Status ---------- */
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(task.priority.color.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
            )

            Text(
                text = task.priority.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF212121)
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .background(
                        when (task.status) {
                            TaskStatus.PENDING -> Color(0xFFFFA000)
                            TaskStatus.IN_PROGRESS -> Color(0xFF29B6F6)
                            TaskStatus.COMPLETED -> Color(0xFF66BB6A)
                        },
                        RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = task.status.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        /* ---------- Task Owner ---------- */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Owner",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF757575)
            )
            Text(
                text = task.owner.ifEmpty { "Unassigned" },
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF212121)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        /* ---------- Description ---------- */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Description",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF757575)
            )
            Text(
                text = task.description.ifEmpty { "No description provided." },
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF212121)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        /* ---------- Timestamps / Metadata ---------- */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Created: ${task.createdAt.toFormattedDate()}",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF9E9E9E)
            )
            // You can add modified / due dates here if needed
        }
    }
}

/* Optional: Extension to format timestamp */
fun Long.toFormattedDate(): String {
    val sdf = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(this))
}
