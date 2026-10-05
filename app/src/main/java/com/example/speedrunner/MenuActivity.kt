package com.example.speedrunner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<Button>(R.id.playButton).setOnClickListener {
            startActivity(Intent(this, GameplayActivity::class.java))
        }

        findViewById<Button>(R.id.characterButton).setOnClickListener {
            startActivity(Intent(this, CharacterSelectionActivity::class.java))
        }

        findViewById<Button>(R.id.highScoreButton).setOnClickListener {
            startActivity(Intent(this, HighScoreActivity::class.java))
        }

        findViewById<Button>(R.id.settingsButton).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        findViewById<Button>(R.id.quitButton).setOnClickListener {
            finishAffinity()
        }
    }
}
