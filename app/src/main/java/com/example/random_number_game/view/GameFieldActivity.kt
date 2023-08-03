package com.example.random_number_game.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.random_number_game.R
import com.example.random_number_game.service.impl.RandomOrgServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameFieldActivity : AppCompatActivity() {
    private var attempts = 0
    private var randomNumber = 0
    private lateinit var preferences: SharedPreferences
    private val lostMessage = "You lost. Try again"
    private val winMessage = "Congratulations, you won! Try again"
    private val lessNotification = "Your number is less than needed"
    private val biggerNotification = "Your number is bigger than needed"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_field)
        preferences = getSharedPreferences("MyGamePreferences", Context.MODE_PRIVATE)
        if (randomNumber == 0 && attempts == 0) {
            fetchRandomNumber()
            attempts = 4
        }
    }

    /**
     * This method is called when the "Confirm" button is clicked. It checks the user's input value
     * and compares it with the random number. If the input value matches the random number, the
     * user wins and the result activity is started with a win message. If the attempts reach 0
     * and the input value is not correct, the user loses and the result activity is started with
     * a lose message. Otherwise, the user receives feedback about whether their guess is higher
     * or lower than the random number.
     *
     * @param view The view that was clicked (the "Confirm" button).
     */
    fun checkAnswer(view: View) {
        val value = getUserInputValue() ?: return

        if (attempts == 0 && value != randomNumber) {
            randomNumber = 0
            startActivity(Intent(this, ResultActivity::class.java).putExtra("message", lostMessage))
        }

        if (value == randomNumber) {
            randomNumber = 0
            startActivity(Intent(this, ResultActivity::class.java).putExtra("message", winMessage))
        } else if (value > randomNumber) {
            attempts--
            findViewById<TextView>(R.id.gameFieldSuggestions).apply {
                text = biggerNotification
            }
        } else {
            attempts--
            findViewById<TextView>(R.id.gameFieldSuggestions).apply {
                text = lessNotification
            }
        }
    }

    /**
     * This method fetches a new random number from the server using the RandomOrgServiceImpl class.
     * It saves the random number to shared preferences and prints the number to the console.
     */
    private fun fetchRandomNumber() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                randomNumber = RandomOrgServiceImpl().fetchRandomNumber()
                println("Random Number: $randomNumber")
                saveRandomNumber(randomNumber)
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    /**
     * This method gets the user's input value from the text field. If the input is empty, it returns
     * null, otherwise, it parses the input value to an integer and returns it.
     *
     * @return The user's input value as an Integer, or null if the input is empty.
     */
    private fun getUserInputValue(): Int? {
        val editTextNumber = findViewById<EditText>(R.id.gameFieldEditTextNumber)
        val valueString = editTextNumber.text.toString().trim()
        if (valueString.isEmpty()) {
            return null
        }
        editTextNumber.setText("")
        return valueString.toIntOrNull()
    }

    /**
     * This method saves the random number to shared preferences using the preferences editor.
     *
     * @param number The random number to be saved.
     */
    private fun saveRandomNumber(number: Int) {
        val editor = preferences.edit()
        editor.putInt("randomNumber", number)
        editor.apply()
    }
}