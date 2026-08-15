package com.kidsdrive.adventure.data

object MissionCatalog {
    val missions = listOf(
        MissionDef(0, "Collect 20 coins in one ride", 20, 30, MissionType.COLLECT_COINS),
        MissionDef(1, "Survive 60 seconds", 60, 40, MissionType.SURVIVE_SECONDS),
        MissionDef(2, "Reach a score of 500", 500, 50, MissionType.REACH_SCORE),
        MissionDef(3, "Collect 10 stars in total", 10, 60, MissionType.COLLECT_STARS),
        MissionDef(4, "Collect 50 coins in one ride", 50, 80, MissionType.COLLECT_COINS),
        MissionDef(5, "Survive 120 seconds", 120, 100, MissionType.SURVIVE_SECONDS),
        MissionDef(6, "Reach a score of 1500", 1500, 120, MissionType.REACH_SCORE)
    )
}
