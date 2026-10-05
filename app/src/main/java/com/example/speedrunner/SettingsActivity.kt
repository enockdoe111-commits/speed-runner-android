package com.example.speedrunner

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CharacterSelectionActivity : AppCompatActivity() {
    private val saveManager by lazy { SaveManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        val holder = findViewById<LinearLayout>(R.id.characterHolder)
        val characters = listOf(
            Character("Runner One", "Free", true),
            Character("Street Ace", "200 coins", false),
            Character("Ice Blazer", "Reach 500 score", false)
        )

        for (character in characters) {
            val button = Button(this)
            button.text = "${character.name} - ${character.unlockText}"
            if (character.unlocked) {
                button.setOnClickListener {
                    saveManager.selectedCharacter = character.name
                    Toast.makeText(this, "Selected ${character.name}", Toast.LENGTH_SHORT).show()
                }
            } else {
                button.setOnClickListener {
                    val totalCoins = saveManager.totalCoins
                    val bestScore = saveManager.highScore
                    if (character.name == "Street Ace" && totalCoins >= 200) {
                        saveManager.unlockCharacter(character.name)
                        saveManager.selectedCharacter = character.name
                        Toast.makeText(this, "Unlocked ${character.name}", Toast.LENGTH_SHORT).show()
                        recreate()
                    } else if (character.name == "Ice Blazer" && bestScore >= 500) {
                        saveManager.unlockCharacter(character.name)
                        saveManager.selectedCharacter = character.name
                        Toast.makeText(this, "Unlocked ${character.name}", Toast.LENGTH_SHORT).show()
                        recreate()
                    } else {
                        Toast.makeText(this, "Requirements not met", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            holder.addView(button)
        }

        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()
        }
    }

    data class Character(val name: String, val unlockText: String, val unlocked: Boolean)
}
