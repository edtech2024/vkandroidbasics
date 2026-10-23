package com.example.vk_android_basics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AppsViewModel : ViewModel() {

    // Внутреннее изменяемое состояние
    private val _uiState = MutableStateFlow(AppsListUiState())
    // Внешнее состояние только для чтения
    val uiState: StateFlow<AppsListUiState> = _uiState.asStateFlow()

    // Канал одноразовых событий для Snackbar
    private val _effect = Channel<AppsUiEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        loadData()
    }

    private fun loadData() {
        // --- ДАННЫЕ ДЛЯ ОСНОВНОГО ВЕРТИКАЛЬНОГО СПИСКА ---
        val myMainList = listOf(
            AppInfo(id = 1, title = "ВКонтакте", description = "Социальная сеть для общения", category = "Социальные", iconRes = android.R.drawable.sym_def_app_icon),
            AppInfo(id = 2, title = "VK Музыка", description = "Миллионы треков и плейлистов", category = "Музыка", iconRes = android.R.drawable.sym_def_app_icon),
            AppInfo(id = 3, title = "VK Видео", description = "Шоу, сериалы и блоги", category = "Видео", iconRes = android.R.drawable.sym_def_app_icon),
            AppInfo(id = 4, title = "Сферум", description = "Образовательное пространство", category = "Образование", iconRes = android.R.drawable.sym_def_app_icon)
        )

        // --- ДАННЫЕ ДЛЯ ГОРИЗОНТАЛЬНОЙ ВИТРИНЫ PAGER (Всего 9 штук из ДЗ 4 Часть 2) ---
        val myShowcaseList = listOf(
            // Страница 1
            AppInfo(id = 5, title = "VK Мессенджер", description = "Бесплатные звонки и чаты", category = "Социальные", iconRes = android.R.drawable.sym_def_app_icon),
            AppInfo(id = 6, title = "VK Клипы", description = "Короткие вертикальные видео", category = "Видео", iconRes = android.R.drawable.sym_def_app_icon),
            AppInfo(id = 7, title = "VK Игры", description = "Каталог мобильных игр", category = "Игры", iconRes = android.R.drawable.sym_def_app_icon),
            // Страница 2
            AppInfo(id = 8, title = "Шаги ВКонтакте", description = "Трекер активности", category = "Здоровье", iconRes = android.R.drawable.sym_def_app_icon),
            AppInfo(id = 9, title = "VK Знакомства", description = "Поиск пары", category = "Социальные", iconRes = android.R.drawable.sym_def_app_icon),
            AppInfo(id = 10, title = "Маруся", description = "Голосовой ассистент", category = "Инструменты", iconRes = android.R.drawable.sym_def_app_icon),
            // Страница 3
            AppInfo(id = 11, title = "VK Почта", description = "Почтовый ящик", category = "Инструменты", iconRes = android.R.drawable.sym_def_app_icon),
            AppInfo(id = 12, title = "VK ID", description = "Единый аккаунт", category = "Безопасность", iconRes = android.R.drawable.sym_def_app_icon),
            AppInfo(id = 13, title = "VK Капсула", description = "Умная колонка", category = "Гаджеты", iconRes = android.R.drawable.sym_def_app_icon)
        )

        // Публикуем данные в состояние UI
        _uiState.value = AppsListUiState(
            mainAppsList = myMainList,
            showcaseAppsList = myShowcaseList,
            isLoading = false
        )
    }

    // Метод обработки нажатия на Тулбар
    fun onToolbarClick() {
        viewModelScope.launch {
            _effect.send(AppsUiEffect.ShowSnackbar("Клик по Тулбару обработан во ViewModel!"))
        }
    }
}
