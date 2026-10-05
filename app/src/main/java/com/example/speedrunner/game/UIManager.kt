package com.example.speedrunner.game

import kotlin.random.Random

class WorldGenerator {
    private val random = Random(System.currentTimeMillis())

    fun generatePattern(viewWidth: Float, viewHeight: Float, obstacleManager: ObstacleManager, coinManager: CoinManager, powerUpManager: PowerUpManager) {
        val startX = viewWidth + 60f
        val laneY = viewHeight - 120f

        when (random.nextInt(5)) {
            0 -> {
                obstacleManager.add(Obstacle(startX, laneY - 30f, 80f, 50f, ObstacleType.BARRIER))
                for (i in 0..5) {
                    coinManager.add(Coin(startX + 40f + i * 35f, laneY - 90f, 12f))
                }
            }
            1 -> {
                obstacleManager.add(Obstacle(startX + 20f, laneY - 20f, 70f, 40f, ObstacleType.SPIKE))
                obstacleManager.add(Obstacle(startX + 120f, laneY - 30f, 70f, 50f, ObstacleType.BARRIER))
            }
            2 -> {
                obstacleManager.add(Obstacle(startX, laneY - 20f, 90f, 40f, ObstacleType.LOW_GROUND))
                for (i in 0..7) {
                    coinManager.add(Coin(startX + 20f + i * 28f, laneY - 100f, 12f))
                }
            }
            3 -> {
                obstacleManager.add(Obstacle(startX + 40f, laneY - 80f, 110f, 80f, ObstacleType.HIGH_CEILING))
                for (i in 0..4) {
                    coinManager.add(Coin(startX + 50f + i * 50f, laneY - 120f, 12f))
                }
            }
            else -> {
                obstacleManager.add(Obstacle(startX + 70f, laneY - 34f, 80f, 44f, ObstacleType.ENEMY, moving = true, movementSpeed = 90f))
                for (i in 0..6) {
                    coinManager.add(Coin(startX + 60f + i * 38f, laneY - 130f, 12f))
                }
            }
        }

        if (random.nextBoolean()) {
            val powerY = laneY - 90f
            val powerType = listOf(PowerType.SHIELD, PowerType.MAGNET, PowerType.BOOST, PowerType.MULTIPLIER).random()
            powerUpManager.add(PowerUp(startX + 200f, powerY, 16f, powerType))
        }
    }
}
