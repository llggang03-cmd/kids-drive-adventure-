package com.kidsdrive.adventure.ui.game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.kidsdrive.adventure.data.CarDef
import com.kidsdrive.adventure.game.GameEngine
import com.kidsdrive.adventure.ui.theme.AccentYellow
import com.kidsdrive.adventure.ui.theme.GrassGreenDay
import com.kidsdrive.adventure.ui.theme.GrassGreenNight
import com.kidsdrive.adventure.ui.theme.RoadGray
import com.kidsdrive.adventure.ui.theme.RoadLine
import com.kidsdrive.adventure.ui.theme.SkyDayBottom
import com.kidsdrive.adventure.ui.theme.SkyDayTop
import com.kidsdrive.adventure.ui.theme.SkyNightBottom
import com.kidsdrive.adventure.ui.theme.SkyNightTop

private val NIGHT_STAR_POSITIONS = listOf(
    0.10f to 0.08f, 0.25f to 0.20f, 0.45f to 0.06f,
    0.60f to 0.15f, 0.15f to 0.28f, 0.80f to 0.22f
)

fun DrawScope.drawGameFrame(engine: GameEngine, car: CarDef) {
    val w = size.width
    val h = size.height

    val skyTop = if (engine.isNight) SkyNightTop else SkyDayTop
    val skyBottom = if (engine.isNight) SkyNightBottom else SkyDayBottom
    drawRect(
        brush = Brush.verticalGradient(listOf(skyTop, skyBottom), startY = 0f, endY = h * 0.42f),
        topLeft = Offset(0f, 0f),
        size = Size(w, h * 0.42f)
    )

    val grassColor = if (engine.isNight) GrassGreenNight else GrassGreenDay
    drawRect(color = grassColor, topLeft = Offset(0f, h * 0.40f), size = Size(w, h * 0.60f))

    val celestialColor = if (engine.isNight) Color(0xFFF4F1DE) else Color(0xFFFFF3B0)
    drawCircle(color = celestialColor, radius = w * 0.09f, center = Offset(w * 0.8f, h * 0.14f))

    if (engine.isNight) {
        NIGHT_STAR_POSITIONS.forEach { (fx, fy) ->
            drawCircle(color = Color.White, radius = w * 0.006f, center = Offset(w * fx, h * fy))
        }
    }

    val roadLeft = w * 0.08f
    val roadWidth = w * 0.84f
    val roadTop = h * 0.34f
    drawRect(color = RoadGray, topLeft = Offset(roadLeft, roadTop), size = Size(roadWidth, h - roadTop))

    drawRect(color = AccentYellow, topLeft = Offset(roadLeft - w * 0.015f, roadTop), size = Size(w * 0.015f, h - roadTop))
    drawRect(color = AccentYellow, topLeft = Offset(roadLeft + roadWidth, roadTop), size = Size(w * 0.015f, h - roadTop))

    val laneCount = engine.laneCount
    val laneWidth = roadWidth / laneCount
    val dashSpacing = h * 0.12f
    for (lane in 1 until laneCount) {
        val x = roadLeft + laneWidth * lane
        var y = roadTop - ((engine.elapsedSeconds * engine.speed) % dashSpacing)
        while (y < h) {
            if (y > roadTop) {
                drawRect(
                    color = RoadLine,
                    topLeft = Offset(x - w * 0.006f, y),
                    size = Size(w * 0.012f, h * 0.05f)
                )
            }
            y += dashSpacing
        }
    }

    fun laneCenterX(lane: Float): Float = roadLeft + laneWidth * (lane + 0.5f)

    val objSize = laneWidth * 0.55f
    engine.roadObjects.forEach { obj ->
        val cx = laneCenterX(obj.lane.toFloat())
        val cy = roadTop + obj.progress * (h - roadTop)
        if (cy in (roadTop - objSize)..(h + objSize)) {
            drawRoadObject(obj.type, cx, cy, objSize)
        }
    }

    val playerY = roadTop + 0.86f * (h - roadTop)
    val playerX = laneCenterX(engine.playerLaneVisual)
    val carWidth = laneWidth * 0.7f
    val carHeight = carWidth * 1.5f
    drawCuteCar(playerX, playerY, carWidth, carHeight, Color(car.bodyColor), Color(car.accentColor))
}
