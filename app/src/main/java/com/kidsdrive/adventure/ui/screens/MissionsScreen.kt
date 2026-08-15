package com.kidsdrive.adventure.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import com.kidsdrive.adventure.data.MissionCatalog
import com.kidsdrive.adventure.data.MissionDef
import com.kidsdrive.adventure.data.SaveState
import com.kidsdrive.adventure.ui.theme.CardGray
import com.kidsdrive.adventure.ui.theme.PrimaryOrange
import com.kidsdrive.adventure.ui.theme.SoftWhite

@Composable
fun MissionsScreen(
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
            Text("Missions", style = MaterialTheme.typography.headlineMedium)
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(MissionCatalog.missions) { mission ->
                MissionRow(
                    mission = mission,
                    progress = saveState.missionProgress[mission.id] ?: 0,
                    claimed = saveState.claimedMissionIds.contains(mission.id),
                    onClaim = {
                        soundManager.playUnlock()
                        onSaveState(
                            saveState.copy(
                                totalCoins = saveState.totalCoins + mission.rewardCoins,
                                claimedMissionIds = saveState.claimedMissionIds + mission.id
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun MissionRow(
    mission: MissionDef,
    progress: Int,
    claimed: Boolean,
    onClaim: () -> Unit
) {
    val achieved = progress >= mission.target
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardGray)
            .padding(16.dp)
    ) {
        Text(mission.description, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = (progress.toFloat() / mission.target.toFloat()).coerceIn(0f, 1f),
            modifier = Modifier.fillMaxWidth(),
            color = PrimaryOrange
        )
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${progress.coerceAtMost(mission.target)}/${mission.target}")
            when {
                claimed -> Text("Claimed ✓", color = Color.Gray)
                achieved -> Button(onClick = onClaim) { Text("Claim 🪙${mission.rewardCoins}") }
                else -> Text("Reward: 🪙${mission.rewardCoins}", color = Color.Gray)
            }
        }
    }
}
