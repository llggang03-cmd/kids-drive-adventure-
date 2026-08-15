package com.kidsdrive.adventure.game

import kotlin.random.Random

enum class RoadObjectType { COIN, STAR, CONE, ROCK, DUCK }

data class RoadObject(
    val id: Long,
    val lane: Int,
    var progress: Float,
    val type: RoadObjectType,
    var collected: Boolean = false
)

class GameEngine(
    private val onCoinCollected: () -> Unit = {},
    private val onStarCollected: () -> Unit = {},
    private val onGameOver: () -> Unit = {}
) {
    val laneCount = 3

    var playerLane = 1
        private set
    var playerLaneVisual = 1f
        private set

    var score = 0
        private set
    var coinsCollected = 0
        private set
    var starsCollected = 0
        private set
    var elapsedSeconds = 0f
        private set
    var speed = BASE_SPEED
        private set
    var isGameOver = false
        private set
    var isNight = false
        private set

    val roadObjects = mutableListOf<RoadObject>()

    private var spawnTimer = 0f
    private var nextId = 0L
    private var dayNightTimer = 0f

    fun reset() {
        playerLane = 1
        playerLaneVisual = 1f
        score = 0
        coinsCollected = 0
        starsCollected = 0
        elapsedSeconds = 0f
        speed = BASE_SPEED
        isGameOver = false
        isNight = false
        roadObjects.clear()
        spawnTimer = 0f
        dayNightTimer = 0f
        nextId = 0L
    }

    fun moveLeft() {
        if (!isGameOver && playerLane > 0) playerLane -= 1
    }

    fun moveRight() {
        if (!isGameOver && playerLane < laneCount - 1) playerLane += 1
    }

    fun update(dt: Float) {
        if (isGameOver) return

        elapsedSeconds += dt
        score += (dt * speed * 0.05f).toInt().coerceAtLeast(0)

        val target = playerLane.toFloat()
        playerLaneVisual += (target - playerLaneVisual) * (dt * 10f).coerceIn(0f, 1f)

        speed = BASE_SPEED + elapsedSeconds * 4.5f

        dayNightTimer += dt
        if (dayNightTimer >= DAY_NIGHT_PERIOD_SECONDS) {
            dayNightTimer = 0f
            isNight = !isNight
        }

        spawnTimer -= dt
        if (spawnTimer <= 0f) {
            spawnRow()
            val speedFactor = (speed - BASE_SPEED) * 0.0006f
            spawnTimer = (0.85f - speedFactor).coerceIn(0.42f, 0.85f)
        }

        val progressPerSecond = speed / 900f
        val iterator = roadObjects.iterator()
        while (iterator.hasNext()) {
            val obj = iterator.next()
            obj.progress += dt * progressPerSecond

            if (!obj.collected && obj.lane == playerLane &&
                obj.progress in (PLAYER_ROW - HIT_WINDOW)..(PLAYER_ROW + HIT_WINDOW)
            ) {
                obj.collected = true
                when (obj.type) {
                    RoadObjectType.COIN -> {
                        coinsCollected += 1
                        score += 10
                        onCoinCollected()
                    }
                    RoadObjectType.STAR -> {
                        starsCollected += 1
                        score += 25
                        onStarCollected()
                    }
                    RoadObjectType.CONE, RoadObjectType.ROCK, RoadObjectType.DUCK -> {
                        isGameOver = true
                        onGameOver()
                    }
                }
            }

            if (obj.progress > 1.15f) {
                iterator.remove()
            }
        }
    }

    private fun spawnRow() {
        val obstacleLane = if (Random.nextFloat() < 0.55f) Random.nextInt(laneCount) else -1
        for (lane in 0 until laneCount) {
            if (lane == obstacleLane) {
                val type = OBSTACLE_TYPES.random()
                roadObjects.add(RoadObject(nextId++, lane, 0f, type))
            } else if (Random.nextFloat() < 0.5f) {
                val type = if (Random.nextFloat() < 0.12f) RoadObjectType.STAR else RoadObjectType.COIN
                roadObjects.add(RoadObject(nextId++, lane, 0f, type))
            }
        }
    }

    companion object {
        private const val BASE_SPEED = 260f
        private const val DAY_NIGHT_PERIOD_SECONDS = 22f
        private const val PLAYER_ROW = 0.86f
        private const val HIT_WINDOW = 0.06f
        private val OBSTACLE_TYPES = listOf(RoadObjectType.CONE, RoadObjectType.ROCK, RoadObjectType.DUCK)
    }
}
