package com.example.speedrunner.game

import android.graphics.RectF
import kotlin.random.Random

enum class ObstacleType {
    BARRIER,
    SPIKE,
    LOW_GROUND,
    HIGH_CEILING,
    ENEMY,
    GAP
}

class Obstacle(
    var x: Float,
    var y: Float,
    var width: Float,
    var height: Float,
    var type: ObstacleType,
    var moving: Boolean = false,
    var movementDir: Float = 1f,
    var movementSpeed: Float = 0f
) {
    fun update(dt: Float, worldSpeed: Float) {
        x -= worldSpeed * dt
        if (moving) {
            y += movementDir * movementSpeed * dt
            if (y < 0f || y > 120f) movementDir *= -1f
        }
    }

    fun rect(): RectF = RectF(x, y, x + width, y + height)
}

class ObstacleManager {
    val obstacles = mutableListOf<Obstacle>()

    fun update(dt: Float, worldSpeed: Float) {
        val iterator = obstacles.iterator()
        while (iterator.hasNext()) {
            val obs = iterator.next()
            obs.update(dt, worldSpeed)
            if (obs.x + obs.width < -50f) {
                iterator.remove()
            }
        }
    }

    fun clear() = obstacles.clear()

    fun add(obstacle: Obstacle) {
        obstacles.add(obstacle)
    }
}
