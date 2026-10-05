package com.example.speedrunner.game

class UIManager {
    fun formatScore(score: Float): String = "${score.toInt()}"
    fun formatDistance(distance: Float): String = "${distance.toInt()}m"
}
