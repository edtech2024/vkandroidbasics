package com.example.vk_android_basics

import androidx.annotation.DrawableRes

data class AppInfo(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    @DrawableRes val iconRes: Int
)

// Список для главного экрана (5 элементов)
val sampleAppsList = listOf(
    AppInfo(1, "Messenger Plus", "Быстрый и безопасный обмен сообщениями по всему миру.", "Социальные сети", android.R.drawable.ic_menu_send),
    AppInfo(2, "Photo Master", "Профессиональный редактор фотографий с AI-фильтрами.", "Фото и видео", android.R.drawable.ic_menu_camera),
    AppInfo(3, "Task Flow", "Управляйте своими задачами и повышайте продуктивность каждый день.", "Продуктивность", android.R.drawable.ic_menu_agenda),
    AppInfo(4, "Fit Life", "Тренировки дома, подсчет калорий и персональный трекер привычек.", "Здоровье и фитнес", android.R.drawable.ic_menu_my_calendar),
    AppInfo(5, "Eco Map", "Найдите пункты переработки мусора и экологичные магазины в вашем городе.", "Карты и навигация", android.R.drawable.ic_dialog_map)
)

// Новые 9 приложений для блока «Витрина» (по 3 на страницу)
val showcaseAppsList = listOf(
    // Страница 1
    AppInfo(101, "Super Game", "Захватывающий экшен-симулятор с крутой графикой.", "Игры", android.R.drawable.ic_media_play),
    AppInfo(102, "Cook Book", "Тысячи пошаговых рецептов от мировых шеф-поваров.", "Еда и напитки", android.R.drawable.ic_menu_compass),
    AppInfo(103, "Weather Now", "Самый точный локальный прогноз погоды на каждый день.", "Погода", android.R.drawable.ic_menu_day),
    // Страница 2
    AppInfo(104, "Music Box", "Слушайте любимые треки и собирайте плейлисты бесплатно.", "Музыка", android.R.drawable.ic_media_next),
    AppInfo(105, "Language Pro", "Интерактивные курсы для быстрого изучения языков.", "Образование", android.R.drawable.ic_menu_sort_alphabetically),
    AppInfo(106, "Smart Home", "Удобное управление всеми умными устройствами в доме.", "Дом", android.R.drawable.ic_lock_lock),
    // Страница 3
    AppInfo(107, "Finance App", "Учет повседневных расходов, доходов и планирование бюджета.", "Финансы", android.R.drawable.ic_menu_report_image),
    AppInfo(108, "News Line", "Свежие мировые новости и аналитические статьи каждый час.", "Новости", android.R.drawable.ic_menu_slideshow),
    AppInfo(109, "Movie Finder", "Удобный поиск фильмов, сериалов и честные рейтинги.", "Видео", android.R.drawable.ic_media_pause)
)
