package com.kidsdrive.adventure.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidsdrive.adventure.audio.SoundManager
import com.kidsdrive.adventure.data.CarCatalog
import com.kidsdrive.adventure.data.SaveState
import com.kidsdrive.adventure.ui.game.drawCuteCar
import com.kidsdrive.adventure.ui.theme.GrassGreenDay
import com.kidsdrive.adventure.ui.theme.PrimaryOrange
import com.kidsdrive.adventure.ui.theme.SkyDayBottom
import com.kidsdrive.adventure.ui.theme.SkyDayTop
import com.kidsdrive.adventure.ui.theme.TextDark

@Composable
fun MainMenuScreen(
    saveState: SaveState,
    onPlay: () -> Unit,
    onGarage: () -> Unit,
    onMissions: () -> Unit,
    soundManager: SoundManager
) {
    val selectedCar = CarCatalog.byId(saveState.selectedCarId)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(SkyDayTop, SkyDayBottom, GrassGreenDay)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            Text(
                "Kids Drive\nAdventure",
                style = MaterialTheme.typography.headlineLarge,
                color = TextDark,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text("🏆 High Score: ${saveState.highScore}", color = TextDark)

            Spacer(Modifier.weight(1f))

            Canvas(modifier = Modifier.size(160.dp, 220.dp)) {
                drawCuteCar(
                    centerX = size.width / 2f,
                    centerY = size.height / 2f,
                    width = size.width * 0.7f,
                    height = size.height * 0.55f,
                    bodyColor = Color(selectedCar.bodyColor),
                    accentColor = Color(selectedCar.accentColor)
                )
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    soundManager.playClick()
                    onPlay()
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
            ) {
                Text("PLAY", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            Spacer(Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = { soundManager.playClick(); onGarage() },
                    shape = RoundedCornerShape(20.dp)
                ) { Text("🚗 Garage") }
                OutlinedButton(
                    onClick = { soundManager.playClick(); onMissions() },
                    shape = RoundedCornerShape(20.dp)
                ) { Text("🎯 Missions") }
            }

            Spacer(Modifier.height(8.dp))
            Text("🪙 ${saveState.totalCoins}   ⭐ ${saveState.totalStars}", color = TextDark)
            Spacer(Modifier.height(16.dp))
        }
    }
}
