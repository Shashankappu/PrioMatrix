package com.example.priomatrix

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
@Composable
fun MatrixScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top row
        Row(modifier = Modifier.weight(1f)) {
            MatrixCell(
                text = "Urgent\nImportant",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(2.dp, Color.Black) // right + bottom
            )
            MatrixCell(
                text = "Not Urgent\nImportant",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(2.dp, Color.Black) // left + bottom
            )
        }

        // Bottom row
        Row(modifier = Modifier.weight(1f)) {
            MatrixCell(
                text = "Urgent\nNot Important",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(2.dp, Color.Black) // right + top
            )
            MatrixCell(
                text = "Not Urgent\nNot Important",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(2.dp, Color.Black) // left + top
            )
        }
    }
}


@Composable
fun MatrixCell(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.rotate(-45f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            modifier = Modifier
                .alpha(0.3f),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}