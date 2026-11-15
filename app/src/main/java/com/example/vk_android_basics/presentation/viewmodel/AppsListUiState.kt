package com.example.vk_android_basics.presentation.viewmodel

import com.example.vkandroidbasics.domain.model.AppEntity

data class AppsListUiState(
    val mainAppsList: List<AppEntity> = emptyList(),
    val showcaseAppsList: List<AppEntity> = emptyList(),
    val selectedApp: AppEntity? = null,
    val ratings: Map<String, Float> = emptyMap(),
    val installState: InstallState = InstallState.NotInstalled,
    val isLoading: Boolean = false,
    val error: String? = null
)
