package com.example.vk_android_basics

/**
 * Одноразовые события экрана, которые не должны дублироваться
 * при изменении конфигурации (повороте экрана).
 */
sealed interface AppsUiEffect {
    // Событие, несущее текст для отображения в Snackbar
    data class ShowSnackbar(val message: String) : AppsUiEffect
}
