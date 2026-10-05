package com.example.speedrunner.game

class GameManager {
    var score = 0f
    var distance = 0f
    var lives = 3
    var coins = 0
    var worldSpeed = 440f
    var speedMultiplier = 1f
    var scoreMultiplier = 1f
    var activePower = "None"
    var shield = false
    var magnetTimer = 0f
    var boostTimer = 0f
    var scoreBoostTimer = 0f

    fun update(dt: Float) {
        distance += worldSpeed * dt * 0.04f
        score += dt * 16f * scoreMultiplier
        if (boostTimer > 0f) {
            boostTimer -= dt
            worldSpeed = 640f
        } else {
            worldSpeed = 440f + distance * 0.25f
        }

        if (magnetTimer > 0f) magnetTimer -= dt
        if (scoreBoostTimer > 0f) scoreBoostTimer -= dt
        if (shield) {
            activePower = "Shield"
        } else if (magnetTimer > 0f) {
            activePower = "Magnet"
        } else if (boostTimer > 0f) {
            activePower = "Boost"
        } else if (scoreBoostTimer > 0f) {
            activePower = "2x Score"
        } else {
            activePower = "None"
        }
    }

    fun collectCoin() {
        coins += 1
        score += 25f * scoreMultiplier
    }

    fun hit() {
        if (shield) {
            shield = false
            activePower = "None"
            return
        }
        lives -= 1
    }
}
