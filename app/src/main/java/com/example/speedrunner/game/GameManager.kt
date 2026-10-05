package com.example.speedrunner.game

import android.graphics.RectF

enum class PowerType {
    SHIELD,
    MAGNET,
    BOOST,
    MULTIPLIER
}

class PowerUp(var x: Float, var y: Float, var radius: Float = 18f, var type: PowerType) {
    var collected = false

    fun rect(): RectF = RectF(x - radius, y - radius, x + radius, y + radius)

    fun update(dt: Float, worldSpeed: Float) {
        x -= worldSpeed * dt
    }
}

class PowerUpManager {
    val powerUps = mutableListOf<PowerUp>()

    fun update(dt: Float, worldSpeed: Float) {
        val iterator = powerUps.iterator()
        while (iterator.hasNext()) {
            val p = iterator.next()
            p.update(dt, worldSpeed)
            if (p.x + p.radius < -20f) {
                iterator.remove()
            }
        }
    }

    fun clear() = powerUps.clear()

    fun add(powerUp: PowerUp) {
        powerUps.add(powerUp)
    }
}
