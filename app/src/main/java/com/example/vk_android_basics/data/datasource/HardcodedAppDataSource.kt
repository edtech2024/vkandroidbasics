package com.edtech2024.vkandroidbasics.data.datasource

import android.R
import com.example.vkandroidbasics.data.model.AppDto

class HardcodedAppDataSource {

    fun getMainApps(): List<AppDto> {
        return listOf(
            AppDto(1, "Messenger Plus", "Быстрый и безопасный обмен сообщениями по всему миру.", "Социальные сети", R.drawable.ic_menu_send),
            AppDto(2, "Photo Master", "Профессиональный редактор фотографий с AI-фильтрами.", "Фото и видео", R.drawable.ic_menu_camera),
            AppDto(3, "Task Flow", "Управляйте своими задачами и повышайте продуктивность каждый день.", "Продуктивность", R.drawable.ic_menu_agenda),
            AppDto(4, "Fit Life", "Тренировки дома, подсчет калорий и персональный трекер привычек.", "Здоровье и фитнес", R.drawable.ic_menu_my_calendar),
            AppDto(5, "Eco Map", "Найдите пункты переработки мусора и экологичные магазины в вашем городе.", "Карты и навигация", R.drawable.ic_dialog_map)
        )
    }

    fun getShowcaseApps(): List<AppDto> {
        return listOf(
            // Страница 1
            AppDto(101, "Super Game", "Захватывающий экшен-симулятор с крутой графикой.", "Игры", R.drawable.ic_media_play),
            AppDto(102, "Cook Book", "Тысячи пошаговых рецептов от мировых шеф-поваров.", "Еда и напитки", R.drawable.ic_menu_compass),
            AppDto(103, "Weather Now", "Самый точный локальный прогноз погоды на каждый день.", "Погода", R.drawable.ic_menu_day),
            // Страница 2
            AppDto(104, "Music Box", "Слушайте любимые треки и собирайте плейлисты бесплатно.", "Музыка", R.drawable.ic_media_next),
            AppDto(105, "Language Pro", "Интерактивные курсы для быстрого изучения языков.", "Образование", R.drawable.ic_menu_sort_alphabetically),
            AppDto(106, "Smart Home", "Удобное управление всеми умными устройствами в доме.", "Дом", R.drawable.ic_lock_lock),
            // Страница 3
            AppDto(107, "Finance App", "Учет повседневных расходов, доходов и планирование бюджета.", "Финансы", R.drawable.ic_menu_report_image),
            AppDto(108, "News Line", "Свежие мировые новости и аналитические статьи каждый час.", "Новости", R.drawable.ic_menu_slideshow),
            AppDto(109, "Movie Finder", "Удобное поиск фильмов, сериалов и честные рейтинги.", "Видео", R.drawable.ic_media_pause)
        )
    }
}
