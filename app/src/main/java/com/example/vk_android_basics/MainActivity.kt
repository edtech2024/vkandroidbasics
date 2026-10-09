package com.example.vk_android_basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.Intent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(onNavigateToSecond = { text ->
                // Переход на вторую Activity через явный Intent
                val intent = Intent(this, SecondActivity::class.java).apply {
                    putExtra("EXTRA_TEXT", text)
                }
                startActivity(intent)
            })
        }
    }
}
