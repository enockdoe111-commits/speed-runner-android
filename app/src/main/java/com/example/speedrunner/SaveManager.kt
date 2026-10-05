package com.example.speedrunner

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HighScoreActivity : AppCompatActivity() {
    private val saveManager by lazy { SaveManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        val bestScore = saveManager.highScore
        val bestDistance = saveManager.bestDistance

        findViewById<TextView>(R.id.highScoreText).text = "Best Score: $bestScore"
        findViewById<TextView>(R.id.bestDistanceText).text = "Best Distance: ${bestDistance}m"

        findViewById<Button>(R.id.highScoreBackButton).setOnClickListener { finish() }
    }
}
