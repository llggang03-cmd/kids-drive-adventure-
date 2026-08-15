package com.kidsdrive.adventure.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.kidsdrive.adventure.audio.SoundManager
import com.kidsdrive.adventure.data.CarCatalog
import com.kidsdrive.adventure.data.MissionCatalog
import com.kidsdrive.adventure.data.MissionType
import com.kidsdrive.adventure.data.SaveState
import com.kidsdrive.adventure.game.GameEngine
import com.kidsdrive.adventure.ui.game.drawGameFrame

private const val BREAK_REMINDER_SECONDS = 600f

@Composable
fun GameScreen(
    saveState: SaveState,
    onSaveState: (SaveState) -> Unit,
    onExitToMenu: () -> Unit,
    soundManager: SoundManager
) {
    val selectedCar = remember(saveState.selectedCarId) { CarCatalog.byId(saveState.selectedCarId) }

    val engine = remember {
        GameEngine(
            onCoinCollected = { soundManager.playCoin() },
            onStarCollected = { soundManager.playStar() },
            onGameOver = { soundManager.playCrash() }
        )
    }

    var frameTick by remember { mutableStateOf(0) }
    var isPaused by remember { mutableStateOf(false) }
    var showBreakReminder by remember { mutableStateOf(false) }
    var activeSecondsSinceBreak by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        engine.reset()
    }

    LaunchedEffect(isPaused, showBreakReminder) {
        var lastNanos = 0L
        while (true) {
            withFrameNanos { nanos ->
                if (lastNanos != 0L) {
                    val dt = ((nanos - lastNanos) / 1_000_000_000f).coerceIn(0f, 0.05f)
                    if (!isPaused && !showBreakReminder && !engine.isGameOver) {
                        engine.update(dt)
                        activeSecondsSinceBreak += dt
                        if (activeSecondsSinceBreak >= BREAK_REMINDER_SECONDS) {
                            showBreakReminder = true
                        }
                    }
                }
                lastNanos = nanos
                frameTick++
            }
        }
    }

    LaunchedEffect(engine.isGameOver) {
        if (engine.isGameOver) {
            val missionProgress = saveState.missionProgress.toMutableMap()
            MissionCatalog.missions.forEach { mission ->
                val current = missionProgress[mission.id] ?: 0
                val achieved = when (mission.type) {
                    MissionType.COLLECT_COINS -> engine.coinsCollected
                    MissionType.SURVIVE_SECONDS -> engine.elapsedSeconds.toInt()
                    MissionType.REACH_SCORE -> engine.score
                    MissionType.COLLECT_STARS -> saveState.totalStars + engine.starsCollected
                }
                if (achieved > current) missionProgress[mission.id] = achieved
            }
            onSaveState(
                saveState.copy(
                    highScore = maxOf(saveState.highScore, engine.score),
                    totalCoins = saveState.totalCoins + engine.coinsCollected,
                    totalStars = saveState.totalStars + engine.starsCollected,
                    missionProgress = missionProgress
                )
            )
        }
    }

    @Suppress("UNUSED_EXPRESSION")
    frameTick

    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .pointerInput(isPaused, showBreakReminder, engine.isGameOver) {
                        detectTapGestures {
                            if (!isPaused && !showBreakReminder && !engine.isGameOver) engine.moveLeft()
                        }
                    }
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .pointerInput(isPaused, showBreakReminder, engine.isGameOver) {
                        detectTapGestures {
                            if (!isPaused && !showBreakReminder && !engine.isGameOver) engine.moveRight()
                        }
                    }
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawGameFrame(engine, selectedCar)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HudChip(icon = "🏆", value = "${engine.score}")
            HudChip(icon = "🪙", value = "${engine.coinsCollected}")
            HudChip(icon = "⭐", value = "${engine.starsCollected}")
            IconButtonRound(label = if (isPaused) "▶" else "⏸") {
                if (!engine.isGameOver) isPaused = !isPaused
            }
        }

        if (isPaused && !engine.isGameOver) {
            PauseOverlay(
                onResume = { isPaused = false },
                onRestart = {
                    engine.reset()
                    isPaused = false
                },
                onExit = onExitToMenu
            )
        }

        if (showBreakReminder) {
            BreakReminderDialog(
                onDismiss = {
                    showBreakReminder = false
                    activeSecondsSinceBreak = 0f
                }
            )
        }

        if (engine.isGameOver) {
            GameOverOverlay(
                score = engine.score,
                coins = engine.coinsCollected,
                stars = engine.starsCollected,
                highScore = maxOf(saveState.highScore, engine.score),
                onRestart = {
                    soundManager.playClick()
                    engine.reset()
                },
                onExit = onExitToMenu
            )
        }
    }
}
