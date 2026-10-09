package com.example.vk_android_basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Извлекаем текст из Интента
        val text = intent.getStringExtra("EXTRA_TEXT").orEmpty()

        setContent {
            SecondScreen(receivedText = text)
        }
    }
}
