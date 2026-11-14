package com.example.vkandroidbasics.domain.usecase

import com.example.vkandroidbasics.domain.model.AppEntity
import com.example.vkandroidbasics.domain.repository.AppRepository
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend fun getCatalog(): List<AppEntity> {
        return repository.getCatalog()
    }

    suspend fun getAppById(id: String): AppEntity {
        return repository.getAppById(id)
    }
}