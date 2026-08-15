package com.kidsdrive.adventure.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kidsdrive.adventure.audio.SoundManager
import com.kidsdrive.adventure.data.GameSaveManager
import com.kidsdrive.adventure.data.SaveState

private enum class Screen { MENU, GAME, GARAGE, MISSIONS }

@Composable
fun AppRoot(saveManager: GameSaveManager, soundManager: SoundManager) {
    var screen by remember { mutableStateOf(Screen.MENU) }
    var saveState by remember { mutableStateOf(saveManager.load()) }

    fun updateSave(newState: SaveState) {
        saveState = newState
        saveManager.save(newState)
    }

    when (screen) {
        Screen.MENU -> MainMenuScreen(
            saveState = saveState,
            onPlay = { screen = Screen.GAME },
            onGarage = { screen = Screen.GARAGE },
            onMissions = { screen = Screen.MISSIONS },
            soundManager = soundManager
        )
        Screen.GAME -> GameScreen(
            saveState = saveState,
            onSaveState = ::updateSave,
            onExitToMenu = { screen = Screen.MENU },
            soundManager = soundManager
        )
        Screen.GARAGE -> GarageScreen(
            saveState = saveState,
            onSaveState = ::updateSave,
            onBack = { screen = Screen.MENU },
            soundManager = soundManager
        )
        Screen.MISSIONS -> MissionsScreen(
            saveState = saveState,
            onSaveState = ::updateSave,
            onBack = { screen = Screen.MENU },
            soundManager = soundManager
        )
    }
}
