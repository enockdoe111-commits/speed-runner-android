package com.example.speedrunner.game

import android.graphics.RectF
import kotlin.math.abs

class Coin(var x: Float, var y: Float, var radius: Float = 12f) {
    var collected = false
    fun rect(): RectF = RectF(x - radius, y - radius, x + radius, y + radius)

    fun update(dt: Float, worldSpeed: Float) {
        x -= worldSpeed * dt
    }
}

class CoinManager {
    val coins = mutableListOf<Coin>()

    fun update(dt: Float, worldSpeed: Float) {
        val iterator = coins.iterator()
        while (iterator.hasNext()) {
            val coin = iterator.next()
            coin.update(dt, worldSpeed)
            if (coin.x + coin.radius < -20f) {
                iterator.remove()
            }
        }
    }

    fun clear() = coins.clear()

    fun add(coin: Coin) {
        coins.add(coin)
    }
}
