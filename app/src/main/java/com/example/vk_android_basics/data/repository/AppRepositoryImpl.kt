package com.example.vkandroidbasics.data.repository

import com.example.vk_android_basics.data.remote.AppsApi
import com.example.vk_android_basics.data.remote.getRating
import com.example.vkandroidbasics.data.mapper.AppMapper
import com.example.vkandroidbasics.domain.model.AppEntity
import com.example.vkandroidbasics.domain.repository.AppRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: AppsApi,
    private val mapper: AppMapper
) : AppRepository {

    override suspend fun getCatalog(): List<AppEntity> {
        return api.getCatalog().map { mapper.mapDtoToEntity(it) }
    }

    override suspend fun getAppById(id: String): AppEntity {
        return mapper.mapDtoToEntity(api.getAppDetails(id))
    }

    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        val result = mutableMapOf<String, Float>()
        for (id in ids) {
            result[id] = api.getRating(id)
        }
        return result
    }

    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> = coroutineScope {
        ids.map { id ->
            async { id to api.getRating(id) }
        }.awaitAll().toMap()
    }
}
