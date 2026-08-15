package com.kidsdrive.adventure.data

data class CarDef(
    val id: Int,
    val name: String,
    val bodyColor: Long,
    val accentColor: Long,
    val unlockCost: Int
)

enum class MissionType { COLLECT_COINS, SURVIVE_SECONDS, REACH_SCORE, COLLECT_STARS }

data class MissionDef(
    val id: Int,
    val description: String,
    val target: Int,
    val rewardCoins: Int,
    val type: MissionType
)

data class SaveState(
    val highScore: Int = 0,
    val totalCoins: Int = 0,
    val totalStars: Int = 0,
    val unlockedCarIds: Set<Int> = setOf(0),
    val selectedCarId: Int = 0,
    val missionProgress: Map<Int, Int> = emptyMap(),
    val claimedMissionIds: Set<Int> = emptySet()
)
