package com.example.vk_android_basics

import androidx.annotation.DrawableRes

data class AppInfo(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    @DrawableRes val iconRes: Int
)

val sampleAppsList = listOf(
    AppInfo(
        id = 1,
        title = "Messenger Plus",
        description = "Быстрый и безопасный обмен сообщениями по всему миру.",
        category = "Социальные сети",
        iconRes = android.R.drawable.ic_menu_send
    ),
    AppInfo(
        id = 2,
        title = "Photo Master",
        description = "Профессиональный редактор фотографий с AI-фильтрами.",
        category = "Фото и видео",
        iconRes = android.R.drawable.ic_menu_camera
    ),
    AppInfo(
        id = 3,
        title = "Task Flow",
        description = "Управляйте своими задачами и повышайте продуктивность каждый день.",
        category = "Продуктивность",
        iconRes = android.R.drawable.ic_menu_agenda
    ),
    AppInfo(
        id = 4,
        title = "Fit Life",
        description = "Тренировки дома, подсчет калорий и персональный трекер привычек.",
        category = "Здоровье и фитнес",
        iconRes = android.R.drawable.ic_menu_my_calendar
    ),
    AppInfo(
        id = 5,
        title = "Eco Map",
        description = "Найдите пункты переработки мусора и экологичные магазины в вашем городе.",
        category = "Карты и навигация",
        iconRes = android.R.drawable.ic_dialog_map
    )
)
