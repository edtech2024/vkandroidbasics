package com.example.vkandroidbasics.data.repository

import com.example.vk_android_basics.data.remote.AppsApi
import com.example.vkandroidbasics.data.mapper.AppMapper
import com.example.vkandroidbasics.domain.model.AppEntity
import com.example.vkandroidbasics.domain.repository.AppRepository
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
}