package com.example.vkandroidbasics.data.repository

import com.edtech2024.vkandroidbasics.data.datasource.HardcodedAppDataSource
import com.example.vkandroidbasics.data.mapper.AppMapper
import com.example.vkandroidbasics.domain.model.AppEntity
import com.example.vkandroidbasics.domain.repository.AppRepository

class AppRepositoryImpl(
    private val dataSource: HardcodedAppDataSource,
    private val mapper: AppMapper
) : AppRepository {

    override fun getMainApps(): List<AppEntity> {
        return dataSource.getMainApps().map { mapper.mapDtoToEntity(it) }
    }

    override fun getShowcaseApps(): List<AppEntity> {
        return dataSource.getShowcaseApps().map { mapper.mapDtoToEntity(it) }
    }
}
