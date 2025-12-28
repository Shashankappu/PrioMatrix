package com.example.priomatrix.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun FilterPopUp(
    selectedCompletion: FilterOption.Completion,
    selectedName: FilterOption.Name,
    onCompletionSelected: (FilterOption.Completion) -> Unit,
    onNameSelected: (FilterOption.Name) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
    ) {

        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .padding(16.dp)
            ) {

                Text(
                    text = "Filter tasks",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(16.dp))

                // ---- Completion section ----
                Text(
                    text = "Status",
                    style = MaterialTheme.typography.labelLarge
                )

                Spacer(Modifier.height(8.dp))

                CompletionOption(
                    text = "All",
                    selected = selectedCompletion is FilterOption.Completion.All
                ) {
                    onCompletionSelected(FilterOption.Completion.All)
                }

                CompletionOption(
                    text = "Completed",
                    selected = selectedCompletion is FilterOption.Completion.Completed
                ) {
                    onCompletionSelected(FilterOption.Completion.Completed)
                }

                CompletionOption(
                    text = "Incomplete",
                    selected = selectedCompletion is FilterOption.Completion.Incomplete
                ) {
                    onCompletionSelected(FilterOption.Completion.Incomplete)
                }

                Spacer(Modifier.height(16.dp))

                // ---- Name section ----
                Text(
                    text = "Name",
                    style = MaterialTheme.typography.labelLarge
                )

                Spacer(Modifier.height(8.dp))

                CompletionOption(
                    text = "A → Z",
                    selected = selectedName is FilterOption.Name.AtoZ
                ) {
                    onNameSelected(FilterOption.Name.AtoZ)
                }

                CompletionOption(
                    text = "Z → A",
                    selected = selectedName is FilterOption.Name.ZtoA
                ) {
                    onNameSelected(FilterOption.Name.ZtoA)
                }

                Spacer(Modifier.height(20.dp))

                // ---- Actions ----
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Done")
                    }
                }
            }
        }
    }
}


@Composable
private fun CompletionOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            selected = selected,
            onClick = null
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
