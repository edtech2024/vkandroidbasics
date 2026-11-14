package com.example.vkandroidbasics.domain.repository

import com.example.vkandroidbasics.domain.model.AppEntity

interface AppRepository {
    fun getMainApps(): List<AppEntity>
    fun getShowcaseApps(): List<AppEntity>
}

