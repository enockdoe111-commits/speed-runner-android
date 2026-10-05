package com.example.speedrunner

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.speedrunner.game.GameView

class GameplayActivity : AppCompatActivity() {
    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameplay)

        gameView = findViewById(R.id.gameView)

        findViewById<Button>(R.id.leftButton).setOnClickListener { gameView.handleAction(GameView.Action.LEFT) }
        findViewById<Button>(R.id.rightButton).setOnClickListener { gameView.handleAction(GameView.Action.RIGHT) }
        findViewById<Button>(R.id.jumpButton).setOnClickListener { gameView.handleAction(GameView.Action.JUMP) }
        findViewById<Button>(R.id.slideButton).setOnClickListener { gameView.handleAction(GameView.Action.SLIDE) }
        findViewById<Button>(R.id.pauseButton).setOnClickListener { showPauseDialog() }

        gameView.startGame()
    }

    private fun showPauseDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Paused")
            .setMessage("Resume or quit to menu?")
            .setPositiveButton("Resume") { _, _ -> gameView.resumeGame() }
            .setNegativeButton("Menu") { _, _ ->
                finish()
            }
            .create()

        dialog.setOnShowListener {
            gameView.pauseGame()
        }
        dialog.show()
    }

    override fun onPause() {
        super.onPause()
        gameView.pauseGame()
    }

    override fun onDestroy() {
        super.onDestroy()
        gameView.stopGame()
    }
}
