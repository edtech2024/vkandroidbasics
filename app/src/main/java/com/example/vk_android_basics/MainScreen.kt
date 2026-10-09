package com.example.vk_android_basics

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(onNavigateToSecond: (String) -> Unit) {
    val context = LocalContext.current
    var textInput by remember { mutableStateOf("") }

    // Переменная для хранения текста ошибки. Если null — ошибки нет.
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = textInput,
            onValueChange = {
                textInput = it
                // Сбрасываем ошибку, как только пользователь начал что-то вводить
                if (errorMessage != null) errorMessage = null
            },
            label = { Text("Введите текст или номер телефона") },
            isError = errorMessage != null,
            // Выводим текст ошибки прямо под полем ввода
            supportingText = {
                errorMessage?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 1. Явный Intent
        Button(
            onClick = {
                val trimmed = textInput.trim()
                if (trimmed.isEmpty()) {
                    errorMessage = "Поле не должно быть пустым для перехода"
                } else {
                    onNavigateToSecond(trimmed)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Открыть вторую Activity")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 2. Неявный Intent (Звонок)
        Button(
            onClick = {
                val phone = textInput.trim()
                // Регулярное выражение для базовой проверки номера телефона
                val phoneRegex = Regex("^[+0-9-() ]{3,15}$")

                if (phone.isEmpty()) {
                    errorMessage = "Введите номер телефона"
                } else if (!phone.matches(phoneRegex)) {
                    errorMessage = "Неверный формат номера (используйте цифры и +)"
                } else {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$phone")
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Приложение для звонков не найдено", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Позвонить другу")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 3. Системный Intent (Поделиться)
        Button(
            onClick = {
                val text = textInput.trim()
                if (text.isEmpty()) {
                    errorMessage = "Введите текст, чтобы поделиться"
                } else {
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, text)
                    }
                    val shareIntent = Intent.createChooser(sendIntent, "Поделиться через...")
                    context.startActivity(shareIntent)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Поделиться текстом")
        }
    }
}

