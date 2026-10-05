package com.example.speedrunner

import android.content.Context
import android.content.SharedPreferences

class SaveManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("speed_runner_prefs", Context.MODE_PRIVATE)

    var highScore: Int
        get() = prefs.getInt("high_score", 0)
        set(value) = prefs.edit().putInt("high_score", value).apply()

    var bestDistance: Int
        get() = prefs.getInt("best_distance", 0)
        set(value) = prefs.edit().putInt("best_distance", value).apply()

    var totalCoins: Int
        get() = prefs.getInt("total_coins", 0)
        set(value) = prefs.edit().putInt("total_coins", value).apply()

    var selectedCharacter: String
        get() = prefs.getString("selected_character", "Runner One") ?: "Runner One"
        set(value) = prefs.edit().putString("selected_character", value).apply()

    var musicEnabled: Boolean
        get() = prefs.getBoolean("music_enabled", true)
        set(value) = prefs.edit().putBoolean("music_enabled", value).apply()

    var sfxEnabled: Boolean
        get() = prefs.getBoolean("sfx_enabled", true)
        set(value) = prefs.edit().putBoolean("sfx_enabled", value).apply()

    fun unlockCharacter(name: String) {
        val unlocked = getUnlockedCharacters().toMutableSet()
        unlocked.add(name)
        prefs.edit().putStringSet("unlocked_characters", unlocked).apply()
    }

    fun isUnlocked(name: String): Boolean {
        return getUnlockedCharacters().contains(name)
    }

    private fun getUnlockedCharacters(): Set<String> {
        return prefs.getStringSet("unlocked_characters", setOf("Runner One")) ?: setOf("Runner One")
    }
}
