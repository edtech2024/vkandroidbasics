package com.example.vk_android_basics

/**
 * Состояние экрана списка приложений.
 * Сохраняется во ViewModel и переживает поворот экрана.
 */
data class AppsListUiState(
    val mainAppsList: List<AppInfo> = emptyList(),      // Данные для вертикального списка приложений
    val showcaseAppsList: List<AppInfo> = emptyList(),  // 9 приложений для горизонтальной витрины
    val isLoading: Boolean = false                      // Статус загрузки данных
)
