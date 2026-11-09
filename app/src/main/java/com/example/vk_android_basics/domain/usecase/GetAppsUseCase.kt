package com.example.vkandroidbasics.domain.usecase

import com.example.vkandroidbasics.domain.model.AppEntity
import com.example.vkandroidbasics.domain.repository.AppRepository

class GetAppsUseCase(private val repository: AppRepository) {

    fun getMainList(): List<AppEntity> {
        return repository.getMainApps()
    }

    fun getShowcaseList(): List<AppEntity> {
        return repository.getShowcaseApps()
    }
}

