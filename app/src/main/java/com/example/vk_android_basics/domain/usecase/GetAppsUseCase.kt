package com.example.vkandroidbasics.domain.usecase

import com.example.vkandroidbasics.domain.model.AppEntity
import com.example.vkandroidbasics.domain.repository.AppRepository
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend fun getCatalog(): List<AppEntity> = repository.getCatalog()

    suspend fun getAppById(id: String): AppEntity = repository.getAppById(id)

    suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> =
        repository.loadRatingsSequentially(ids)

    suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> =
        repository.loadRatingsInParallel(ids)
}
