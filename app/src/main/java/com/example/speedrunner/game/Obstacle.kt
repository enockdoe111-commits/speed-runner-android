package com.example.speedrunner.game

import android.graphics.RectF

class Player(
    var x: Float = 180f,
    var width: Float = 70f,
    var height: Float = 100f,
    var y: Float = 0f
) {
    var laneIndex = 1
    var targetX = x
    var alive = true
    var onGround = true
    var velocityY = 0f
    var gravity = 1800f
    var jumpStrength = 900f
    var shield = false
    var invincibleTimer = 0f
    var state = "run"
    var slideTimer = 0f

    fun reset() {
        laneIndex = 1
        x = 180f
        targetX = x
        alive = true
        onGround = true
        velocityY = 0f
        y = 0f
        shield = false
        invincibleTimer = 0f
        slideTimer = 0f
        state = "run"
    }

    fun jump() {
        if (onGround && alive) {
            velocityY = -jumpStrength
            onGround = false
            state = "jump"
        }
    }

    fun slide() {
        if (onGround && alive) {
            slideTimer = 0.55f
            height = 70f
            state = "slide"
        }
    }

    fun moveLeft() {
        laneIndex = (laneIndex - 1).coerceAtLeast(0)
        targetX = 180f + laneIndex * 120f
    }

    fun moveRight() {
        laneIndex = (laneIndex + 1).coerceAtMost(2)
        targetX = 180f + laneIndex * 120f
    }

    fun update(dt: Float) {
        x += (targetX - x) * 0.18f
        if (!onGround) {
            velocityY += gravity * dt
            y += velocityY * dt
        }

        if (y >= 0f) {
            y = 0f
            velocityY = 0f
            onGround = true
            state = "run"
        }

        if (slideTimer > 0f) {
            slideTimer -= dt
            if (slideTimer <= 0f) {
                height = 100f
            }
        }

        if (invincibleTimer > 0f) {
            invincibleTimer -= dt
        }
    }

    fun rect(): RectF = RectF(x, y + 40f, x + width, y + height + 40f)

    fun hit() {
        if (shield) {
            shield = false
            invincibleTimer = 1.2f
            state = "shield"
            return
        }
        alive = false
        state = "hit"
    }
}
