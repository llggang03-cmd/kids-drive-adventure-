package com.kidsdrive.adventure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kidsdrive.adventure.audio.SoundManager
import com.kidsdrive.adventure.data.GameSaveManager
import com.kidsdrive.adventure.ui.screens.AppRoot
import com.kidsdrive.adventure.ui.theme.KidsDriveAdventureTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val saveManager = GameSaveManager(applicationContext)
        val soundManager = SoundManager()

        setContent {
            KidsDriveAdventureApp(saveManager = saveManager, soundManager = soundManager)
        }
    }
}

@Composable
private fun KidsDriveAdventureApp(saveManager: GameSaveManager, soundManager: SoundManager) {
    KidsDriveAdventureTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppRoot(saveManager = saveManager, soundManager = soundManager)
        }
    }
}
