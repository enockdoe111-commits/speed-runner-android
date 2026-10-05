package com.example.speedrunner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val score = intent.getIntExtra("score", 0)
        val distance = intent.getFloatExtra("distance", 0f)
        val coins = intent.getIntExtra("coins", 0)

        findViewById<TextView>(R.id.gameOverSummary).text = "Score: $score\nDistance: ${"%.0f".format(distance)}m\nCoins: $coins"

        findViewById<Button>(R.id.restartButton).setOnClickListener {
            startActivity(Intent(this, GameplayActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.menuButton).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}
