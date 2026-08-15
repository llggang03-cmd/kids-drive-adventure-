package com.kidsdrive.adventure.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kidsdrive.adventure.ui.theme.PrimaryOrange

@Composable
fun GameOverOverlay(
    score: Int,
    coins: Int,
    stars: Int,
    highScore: Int,
    onRestart: () -> Unit,
    onExit: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(24.dp))
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Oops! 🚧", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Text("Let's try again!", color = Color.Gray)
            Spacer(Modifier.height(16.dp))
            Text("Score: $score", fontWeight = FontWeight.SemiBold)
            Text("🪙 Coins: $coins")
            Text("⭐ Stars: $stars")
            Text("🏆 Best: $highScore")
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = onRestart,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                modifier = Modifier.fillMaxWidth()
            ) { Text("Restart") }
            Spacer(Modifier.height(10.dp))
            TextButton(onClick = onExit, modifier = Modifier.fillMaxWidth()) { Text("Exit to Menu") }
        }
    }
}
