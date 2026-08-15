package com.kidsdrive.adventure.data

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

class GameSaveManager(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun load(): SaveState {
        return SaveState(
            highScore = prefs.getInt(KEY_HIGH_SCORE, 0),
            totalCoins = prefs.getInt(KEY_TOTAL_COINS, 0),
            totalStars = prefs.getInt(KEY_TOTAL_STARS, 0),
            unlockedCarIds = stringToIntSet(prefs.getString(KEY_UNLOCKED_CARS, "[0]")),
            selectedCarId = prefs.getInt(KEY_SELECTED_CAR, 0),
            missionProgress = stringToIntMap(prefs.getString(KEY_MISSION_PROGRESS, "{}")),
            claimedMissionIds = stringToIntSet(prefs.getString(KEY_CLAIMED_MISSIONS, "[]"))
        )
    }

    fun save(state: SaveState) {
        prefs.edit()
            .putInt(KEY_HIGH_SCORE, state.highScore)
            .putInt(KEY_TOTAL_COINS, state.totalCoins)
            .putInt(KEY_TOTAL_STARS, state.totalStars)
            .putString(KEY_UNLOCKED_CARS, intSetToString(state.unlockedCarIds))
            .putInt(KEY_SELECTED_CAR, state.selectedCarId)
            .putString(KEY_MISSION_PROGRESS, intMapToString(state.missionProgress))
            .putString(KEY_CLAIMED_MISSIONS, intSetToString(state.claimedMissionIds))
            .apply()
    }

    private fun intSetToString(set: Set<Int>): String = JSONArray(set.toList()).toString()

    private fun stringToIntSet(raw: String?): Set<Int> {
        if (raw.isNullOrBlank()) return emptySet()
        return try {
            val arr = JSONArray(raw)
            (0 until arr.length()).map { arr.getInt(it) }.toSet()
        } catch (e: Exception) {
            emptySet()
        }
    }

    private fun intMapToString(map: Map<Int, Int>): String {
        val obj = JSONObject()
        map.forEach { (key, value) -> obj.put(key.toString(), value) }
        return obj.toString()
    }

    private fun stringToIntMap(raw: String?): Map<Int, Int> {
        if (raw.isNullOrBlank()) return emptyMap()
        return try {
            val obj = JSONObject(raw)
            val result = mutableMapOf<Int, Int>()
            val keys = obj.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                result[key.toInt()] = obj.getInt(key)
            }
            result
        } catch (e: Exception) {
            emptyMap()
        }
    }

    companion object {
        private const val PREFS_NAME = "kids_drive_adventure_prefs"
        private const val KEY_HIGH_SCORE = "high_score"
        private const val KEY_TOTAL_COINS = "total_coins"
        private const val KEY_TOTAL_STARS = "total_stars"
        private const val KEY_UNLOCKED_CARS = "unlocked_cars"
        private const val KEY_SELECTED_CAR = "selected_car"
        private const val KEY_MISSION_PROGRESS = "mission_progress"
        private const val KEY_CLAIMED_MISSIONS = "claimed_missions"
    }
}
