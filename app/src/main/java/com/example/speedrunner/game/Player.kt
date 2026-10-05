package com.example.speedrunner.game

import android.content.Context
import android.media.ToneGenerator
import android.os.Build
import java.util.Locale

class AudioManager(private val context: Context) {
    private val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 80)

    var musicEnabled = true
    var sfxEnabled = true

    fun playJump() = playTone(440)
    fun playCoin() = playTone(880)
    fun playPowerUp() = playTone(1200)
    fun playHit() = playTone(120)
    fun playGameOver() = playTone(60)
    fun playButton() = playTone(700)

    fun playBackgroundMusic() {
        // Placeholder for future asset-based music.
    }

    private fun playTone(freq: Int) {
        if (!sfxEnabled) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            toneGenerator.startTone(freq, 120)
        } else {
            @Suppress("DEPRECATION")
            toneGenerator.startTone(freq, 120)
        }
    }
}
