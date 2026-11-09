package com.example.vkandroidbasics.data.mapper

import com.example.vkandroidbasics.data.model.AppDto
import com.example.vkandroidbasics.domain.model.AppEntity

class AppMapper {
    fun mapDtoToEntity(dto: AppDto): AppEntity {
        return AppEntity(
            id = dto.id ?: 0,
            title = dto.title ?: "Без названия",
            description = dto.description ?: "",
            category = dto.category ?: "Разное",
            iconRes = dto.iconRes ?: android.R.drawable.sym_def_app_icon
        )
    }
}