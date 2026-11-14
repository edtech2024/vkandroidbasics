package com.example.vkandroidbasics.domain.repository

import com.example.vkandroidbasics.domain.model.AppEntity

interface AppRepository {
    suspend fun getCatalog(): List<AppEntity>
    suspend fun getAppById(id: String): AppEntity
}