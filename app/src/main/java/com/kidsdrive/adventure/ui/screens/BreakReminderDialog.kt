package com.kidsdrive.adventure.ui.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kidsdrive.adventure.ui.theme.PrimaryOrange

@Composable
fun BreakReminderDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Time for a break! 🌤️") },
        text = { Text("You've been driving for a while. Stretch, drink some water, and rest your eyes for a bit before your next ride!") },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)) {
                Text("OK, thanks!")
            }
        }
    )
}
