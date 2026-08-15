package com.kidsdrive.adventure.ui.game

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawCuteCar(
    centerX: Float,
    centerY: Float,
    width: Float,
    height: Float,
    bodyColor: Color,
    accentColor: Color
) {
    val left = centerX - width / 2f
    val top = centerY - height / 2f

    drawOval(
        color = Color.Black.copy(alpha = 0.18f),
        topLeft = Offset(left + width * 0.08f, top + height * 0.94f),
        size = Size(width * 0.84f, height * 0.16f)
    )

    drawRoundRect(
        color = bodyColor,
        topLeft = Offset(left, top + height * 0.35f),
        size = Size(width, height * 0.5f),
        cornerRadius = CornerRadius(width * 0.22f, width * 0.22f)
    )

    drawRoundRect(
        color = accentColor,
        topLeft = Offset(left + width * 0.16f, top),
        size = Size(width * 0.68f, height * 0.5f),
        cornerRadius = CornerRadius(width * 0.22f, width * 0.22f)
    )

    drawRoundRect(
        color = Color(0xFFCFEFFF),
        topLeft = Offset(left + width * 0.24f, top + height * 0.08f),
        size = Size(width * 0.52f, height * 0.28f),
        cornerRadius = CornerRadius(width * 0.12f, width * 0.12f)
    )

    drawCircle(color = Color(0xFFFFF6C4), radius = width * 0.07f, center = Offset(left + width * 0.14f, top + height * 0.55f))
    drawCircle(color = Color(0xFFFFF6C4), radius = width * 0.07f, center = Offset(left + width * 0.86f, top + height * 0.55f))

    val wheelRadius = width * 0.16f
    val wheelY = top + height * 0.92f
    drawCircle(color = Color(0xFF2B2B36), radius = wheelRadius, center = Offset(left + width * 0.2f, wheelY))
    drawCircle(color = Color(0xFF2B2B36), radius = wheelRadius, center = Offset(left + width * 0.8f, wheelY))
    drawCircle(color = Color(0xFFB0B0BC), radius = wheelRadius * 0.45f, center = Offset(left + width * 0.2f, wheelY))
    drawCircle(color = Color(0xFFB0B0BC), radius = wheelRadius * 0.45f, center = Offset(left + width * 0.8f, wheelY))

    drawCircle(color = Color(0xFF2B2B36), radius = width * 0.025f, center = Offset(left + width * 0.4f, top + height * 0.22f))
    drawCircle(color = Color(0xFF2B2B36), radius = width * 0.025f, center = Offset(left + width * 0.6f, top + height * 0.22f))
}
