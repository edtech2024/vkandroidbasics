package com.example.vkandroidbasics.data.mapper

import com.example.vkandroidbasics.data.model.AppDto
import com.example.vkandroidbasics.domain.model.AppEntity
import javax.inject.Inject

class AppMapper @Inject constructor() {
    fun mapDtoToEntity(dto: AppDto): AppEntity {
        return AppEntity(
            id = dto.id.orEmpty(),
            title = dto.name ?: "Без названия",
            description = dto.description.orEmpty(),
            category = dto.category ?: "Разное",
            iconUrl = dto.iconUrl.orEmpty()
        )
    }
}
