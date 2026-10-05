package com.example.speedrunner

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    private val saveManager by lazy { SaveManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val musicSwitch = findViewById<Switch>(R.id.musicSwitch)
        val sfxSwitch = findViewById<Switch>(R.id.sfxSwitch)

        musicSwitch.isChecked = saveManager.musicEnabled
        sfxSwitch.isChecked = saveManager.sfxEnabled

        musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveManager.musicEnabled = isChecked
        }

        sfxSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveManager.sfxEnabled = isChecked
        }

        findViewById<Button>(R.id.settingsBackButton).setOnClickListener {
            finish()
        }
    }
}
