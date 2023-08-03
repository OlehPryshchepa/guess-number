package com.example.random_number_game.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.random_number_game.R

class ResultActivity : AppCompatActivity() {
    private var message = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        message = intent.getStringExtra("message").toString()
        findViewById<TextView>(R.id.textViewResult).apply {
            text = message
        }
    }

    fun playAgain(view: View) {
        startActivity(Intent(this, GameFieldActivity::class.java))
    }
}