package com.kidsdrive.adventure.ui.game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.kidsdrive.adventure.game.RoadObjectType
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun DrawScope.drawRoadObject(type: RoadObjectType, centerX: Float, centerY: Float, sizePx: Float) {
    when (type) {
        RoadObjectType.COIN -> drawCoin(centerX, centerY, sizePx)
        RoadObjectType.STAR -> drawStarShape(centerX, centerY, sizePx)
        RoadObjectType.CONE -> drawCone(centerX, centerY, sizePx)
        RoadObjectType.ROCK -> drawRock(centerX, centerY, sizePx)
        RoadObjectType.DUCK -> drawDuck(centerX, centerY, sizePx)
    }
}

private fun DrawScope.drawCoin(cx: Float, cy: Float, s: Float) {
    drawCircle(color = Color(0xFFFFD54F), radius = s * 0.42f, center = Offset(cx, cy))
    drawCircle(color = Color(0xFFFFF3C4), radius = s * 0.24f, center = Offset(cx, cy))
}

private fun DrawScope.drawStarShape(cx: Float, cy: Float, s: Float) {
    val path = Path()
    val outerR = s * 0.5f
    val innerR = s * 0.22f
    for (i in 0 until 10) {
        val angle = -PI / 2 + i * PI / 5
        val r = if (i % 2 == 0) outerR else innerR
        val x = cx + (r * cos(angle)).toFloat()
        val y = cy + (r * sin(angle)).toFloat()
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    drawPath(path, color = Color(0xFFFFA726))
}

private fun DrawScope.drawCone(cx: Float, cy: Float, s: Float) {
    val path = Path().apply {
        moveTo(cx, cy - s * 0.5f)
        lineTo(cx + s * 0.36f, cy + s * 0.42f)
        lineTo(cx - s * 0.36f, cy + s * 0.42f)
        close()
    }
    drawPath(path, color = Color(0xFFFF7043))
    drawRect(
        color = Color.White,
        topLeft = Offset(cx - s * 0.28f, cy + s * 0.1f),
        size = Size(s * 0.56f, s * 0.1f)
    )
    drawRect(
        color = Color(0xFF5D4037),
        topLeft = Offset(cx - s * 0.42f, cy + s * 0.42f),
        size = Size(s * 0.84f, s * 0.08f)
    )
}

private fun DrawScope.drawRock(cx: Float, cy: Float, s: Float) {
    drawCircle(color = Color(0xFF9E9E9E), radius = s * 0.46f, center = Offset(cx, cy))
    drawCircle(
        color = Color(0xFFBDBDBD),
        radius = s * 0.46f,
        center = Offset(cx - s * 0.1f, cy - s * 0.1f),
        style = Stroke(width = s * 0.05f)
    )
    drawCircle(color = Color(0xFF2B2B36), radius = s * 0.035f, center = Offset(cx - s * 0.12f, cy - s * 0.05f))
    drawCircle(color = Color(0xFF2B2B36), radius = s * 0.035f, center = Offset(cx + s * 0.12f, cy - s * 0.05f))
}

private fun DrawScope.drawDuck(cx: Float, cy: Float, s: Float) {
    drawCircle(color = Color(0xFFFFEE58), radius = s * 0.38f, center = Offset(cx, cy + s * 0.05f))
    drawCircle(color = Color(0xFFFFEE58), radius = s * 0.22f, center = Offset(cx, cy - s * 0.28f))
    drawCircle(color = Color(0xFFFF7043), radius = s * 0.08f, center = Offset(cx + s * 0.18f, cy - s * 0.28f))
    drawCircle(color = Color(0xFF2B2B36), radius = s * 0.03f, center = Offset(cx + s * 0.06f, cy - s * 0.32f))
}
