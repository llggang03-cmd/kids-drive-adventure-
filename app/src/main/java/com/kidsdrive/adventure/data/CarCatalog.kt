package com.kidsdrive.adventure.data

object CarCatalog {
    val cars = listOf(
        CarDef(id = 0, name = "Sunny Red", bodyColor = 0xFFE53935, accentColor = 0xFFFFCDD2, unlockCost = 0),
        CarDef(id = 1, name = "Ocean Blue", bodyColor = 0xFF1E88E5, accentColor = 0xFFBBDEFB, unlockCost = 150),
        CarDef(id = 2, name = "Lemon Yellow", bodyColor = 0xFFFDD835, accentColor = 0xFFFFF9C4, unlockCost = 300),
        CarDef(id = 3, name = "Grape Purple", bodyColor = 0xFF8E24AA, accentColor = 0xFFE1BEE7, unlockCost = 500)
    )

    fun byId(id: Int): CarDef = cars.firstOrNull { it.id == id } ?: cars[0]
}
