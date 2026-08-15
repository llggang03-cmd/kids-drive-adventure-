package com.kidsdrive.adventure.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kidsdrive.adventure.audio.SoundManager
import com.kidsdrive.adventure.data.CarCatalog
import com.kidsdrive.adventure.data.CarDef
import com.kidsdrive.adventure.data.SaveState
import com.kidsdrive.adventure.ui.game.drawCuteCar
import com.kidsdrive.adventure.ui.theme.CardGray
import com.kidsdrive.adventure.ui.theme.PrimaryOrange
import com.kidsdrive.adventure.ui.theme.SoftWhite

@Composable
fun GarageScreen(
    saveState: SaveState,
    onSaveState: (SaveState) -> Unit,
    onBack: () -> Unit,
    soundManager: SoundManager
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftWhite)
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(Modifier.width(8.dp))
            Text("Garage", style = MaterialTheme.typography.headlineMedium)
        }
        Spacer(Modifier.height(8.dp))
        Text("🪙 ${saveState.totalCoins} coins", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(CarCatalog.cars) { car ->
                CarTile(
                    car = car,
                    isUnlocked = saveState.unlockedCarIds.contains(car.id),
                    isSelected = saveState.selectedCarId == car.id,
                    canAfford = saveState.totalCoins >= car.unlockCost,
                    onClick = {
                        when {
                            saveState.unlockedCarIds.contains(car.id) -> {
                                soundManager.playClick()
                                onSaveState(saveState.copy(selectedCarId = car.id))
                            }
                            saveState.totalCoins >= car.unlockCost -> {
                                soundManager.playUnlock()
                                onSaveState(
                                    saveState.copy(
                                        totalCoins = saveState.totalCoins - car.unlockCost,
                                        unlockedCarIds = saveState.unlockedCarIds + car.id,
                                        selectedCarId = car.id
                                    )
                                )
                            }
                            else -> soundManager.playClick()
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun CarTile(
    car: CarDef,
    isUnlocked: Boolean,
    isSelected: Boolean,
    canAfford: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) Color(0xFFFFE9D6) else CardGray)
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = PrimaryOrange,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
        ) {
            drawCuteCar(
                centerX = size.width / 2f,
                centerY = size.height / 2f,
                width = size.width * 0.55f,
                height = size.height * 0.85f,
                bodyColor = Color(car.bodyColor),
                accentColor = Color(car.accentColor)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(car.name, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        when {
            isSelected -> Text("Selected ✓", color = PrimaryOrange, fontWeight = FontWeight.SemiBold)
            isUnlocked -> Text("Tap to select")
            canAfford -> Text("Unlock for 🪙 ${car.unlockCost}")
            else -> Text("Need 🪙 ${car.unlockCost}", color = Color.Gray)
        }
    }
}
