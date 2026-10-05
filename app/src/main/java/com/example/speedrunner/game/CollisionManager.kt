package com.example.speedrunner.game

class ScoreManager {
    var score = 0f
    var distance = 0f
    var coins = 0
    var multiplier = 1f

    fun update(dt: Float, worldSpeed: Float) {
        distance += worldSpeed * dt * 0.05f
        score += dt * 16f * multiplier
    }

    fun addCoin() {
        coins += 1
        score += 25f * multiplier
    }
}
