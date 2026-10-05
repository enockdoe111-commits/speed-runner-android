package com.example.speedrunner.game

import android.graphics.RectF
import kotlin.math.abs

class CollisionManager {
    fun handleCollisions(
        player: Player,
        obstacles: List<Obstacle>,
        coins: MutableList<Coin>,
        powerUps: MutableList<PowerUp>,
        gameManager: GameManager,
        scoreManager: ScoreManager,
        audioManager: AudioManager
    ) {
        val playerRect = player.rect()

        for (obstacle in obstacles) {
            if (RectF.intersects(playerRect, obstacle.rect())) {
                player.hit()
                gameManager.hit()
                audioManager.playHit()
                obstacle.x = -1000f
                if (gameManager.lives <= 0) {
                    audioManager.playGameOver()
                }
                return
            }
        }

        for (coin in coins) {
            if (coin.collected) continue
            val dx = coin.x - (player.x + player.width / 2f)
            val dy = coin.y - (player.y + player.height / 2f)
            if (abs(dx) < 45f && abs(dy) < 45f) {
                coin.collected = true
                scoreManager.addCoin()
                gameManager.collectCoin()
                audioManager.playCoin()
                return
            }
        }

        for (powerUp in powerUps) {
            if (powerUp.collected) continue
            if (RectF.intersects(playerRect, powerUp.rect())) {
                powerUp.collected = true
                when (powerUp.type) {
                    PowerType.SHIELD -> {
                        gameManager.shield = true
                        gameManager.activePower = "Shield"
                    }
                    PowerType.MAGNET -> gameManager.magnetTimer = 8f
                    PowerType.BOOST -> gameManager.boostTimer = 6f
                    PowerType.MULTIPLIER -> gameManager.scoreBoostTimer = 8f
                }
                audioManager.playPowerUp()
                return
            }
        }
    }
}
