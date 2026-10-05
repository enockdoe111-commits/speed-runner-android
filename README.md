package com.example.speedrunner.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.example.speedrunner.SaveManager

class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val saveManager = SaveManager(context)
    private val player = Player()
    private val obstacleManager = ObstacleManager()
    private val coinManager = CoinManager()
    private val powerUpManager = PowerUpManager()
    private val worldGenerator = WorldGenerator()
    private val scoreManager = ScoreManager()
    private val audioManager = AudioManager(context)
    private val collisionManager = CollisionManager()
    private val gameManager = GameManager()
    private val uiManager = UIManager()

    enum class Action {
        LEFT,
        RIGHT,
        JUMP,
        SLIDE
    }

    private var running = false
    private var gameOverListener: (() -> Unit)? = null
    private var spawnAccumulator = 0f
    private var currentEnvironment = 0

    init {
        audioManager.musicEnabled = saveManager.musicEnabled
        audioManager.sfxEnabled = saveManager.sfxEnabled
    }

    fun setGameOverListener(listener: () -> Unit) {
        gameOverListener = listener
    }

    fun startGame() {
        running = true
        player.reset()
        obstacleManager.clear()
        coinManager.clear()
        powerUpManager.clear()
        scoreManager.score = 0f
        scoreManager.distance = 0f
        scoreManager.coins = 0
        gameManager.score = 0f
        gameManager.distance = 0f
        gameManager.coins = 0
        gameManager.lives = 3
        spawnAccumulator = 0f
        postGameLoop()
    }

    fun pauseGame() {
        running = false
    }

    fun resumeGame() {
        running = true
        postGameLoop()
    }

    fun stopGame() {
        running = false
        removeCallbacks(gameLoopRunnable)
    }

    fun handleAction(action: Action) {
        when (action) {
            Action.LEFT -> player.moveLeft()
            Action.RIGHT -> player.moveRight()
            Action.JUMP -> player.jump()
            Action.SLIDE -> player.slide()
        }
    }

    private val gameLoopRunnable = object : Runnable {
        override fun run() {
            if (!running) return
            updateGame(0.016f)
            invalidate()
            postOnAnimation(this)
        }
    }

    private fun postGameLoop() {
        removeCallbacks(gameLoopRunnable)
        postOnAnimation(gameLoopRunnable)
    }

    private fun updateGame(dt: Float) {
        if (!running) return

        player.update(dt)
        obstacleManager.update(dt, gameManager.worldSpeed)
        coinManager.update(dt, gameManager.worldSpeed)
        powerUpManager.update(dt, gameManager.worldSpeed)
        gameManager.update(dt)
        scoreManager.update(dt, gameManager.worldSpeed)

        spawnAccumulator += dt
        if (spawnAccumulator > 1.2f) {
            spawnAccumulator = 0f
            worldGenerator.generatePattern(width.toFloat(), height.toFloat(), obstacleManager, coinManager, powerUpManager)
            currentEnvironment = (currentEnvironment + 1) % 5
        }

        collisionManager.handleCollisions(player, obstacleManager.obstacles, coinManager.coins, powerUpManager.powerUps, gameManager, scoreManager, audioManager)

        if (!player.alive || gameManager.lives <= 0) {
            running = false
            val high = saveManager.highScore.coerceAtLeast(scoreManager.score.toInt())
            saveManager.highScore = high
            saveManager.bestDistance = scoreManager.distance.toInt().coerceAtLeast(saveManager.bestDistance)
            saveManager.totalCoins += scoreManager.coins
            gameOverListener?.invoke()
            Toast.makeText(context, "Game over!", Toast.LENGTH_SHORT).show()
            return
        }

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackground(canvas)
        drawGround(canvas)
        drawPlayer(canvas)
        drawObstacles(canvas)
        drawCoins(canvas)
        drawPowerUps(canvas)
        drawHud(canvas)
    }

    private fun drawBackground(canvas: Canvas) {
        val bgColors = listOf(Color.parseColor("#7EC8FF"), Color.parseColor("#8EDC77"), Color.parseColor("#F7D08D"), Color.parseColor("#D9F1FF"), Color.parseColor("#101C3B"))
        val paint = Paint().apply { color = bgColors[currentEnvironment % bgColors.size] }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }

    private fun drawGround(canvas: Canvas) {
        val paint = Paint().apply { color = Color.parseColor("#2F5B3A") }
        canvas.drawRect(0f, height - 120f, width.toFloat(), height.toFloat(), paint)
    }

    private fun drawPlayer(canvas: Canvas) {
        val p = Paint().apply { color = if (player.shield) Color.BLUE else Color.MAGENTA }
        val rect = player.rect()
        canvas.drawRect(rect, p)
        if (player.invincibleTimer > 0f) {
            val outline = Paint().apply { color = Color.WHITE; style = Paint.Style.STROKE; strokeWidth = 3f }
            canvas.drawRect(rect, outline)
        }
    }

    private fun drawObstacles(canvas: Canvas) {
        val paint = Paint().apply { color = Color.RED }
        for (obstacle in obstacleManager.obstacles) {
            val rect = obstacle.rect()
            canvas.drawRect(rect, paint)
        }
    }

    private fun drawCoins(canvas: Canvas) {
        val paint = Paint().apply { color = Color.YELLOW }
        for (coin in coinManager.coins) {
            if (coin.collected) continue
            canvas.drawCircle(coin.x, coin.y, coin.radius, paint)
        }
    }

    private fun drawPowerUps(canvas: Canvas) {
        for (powerUp in powerUpManager.powerUps) {
            if (powerUp.collected) continue
            val paint = Paint().apply {
                color = when (powerUp.type) {
                    PowerType.SHIELD -> Color.BLUE
                    PowerType.MAGNET -> Color.parseColor("#FFB74D")
                    PowerType.BOOST -> Color.GREEN
                    PowerType.MULTIPLIER -> Color.MAGENTA
                }
            }
            canvas.drawCircle(powerUp.x, powerUp.y, powerUp.radius, paint)
        }
    }

    private fun drawHud(canvas: Canvas) {
        val textPaint = Paint().apply {
            color = Color.WHITE
            textSize = 36f
        }
        val scoreText = "Score: ${uiManager.formatScore(scoreManager.score)}"
        canvas.drawText(scoreText, 30f, 45f, textPaint)
        canvas.drawText("Coins: ${scoreManager.coins}", 30f, 85f, textPaint)
        canvas.drawText("Lives: ${gameManager.lives}", width - 200f, 45f, textPaint)
        canvas.drawText("Power: ${gameManager.activePower}", 30f, height - 30f, textPaint)
    }
}
