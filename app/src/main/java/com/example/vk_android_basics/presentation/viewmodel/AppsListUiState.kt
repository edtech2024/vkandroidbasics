package com.example.vk_android_basics.presentation.viewmodel

import com.example.vkandroidbasics.domain.model.AppEntity

/**
 * Состояние экрана списка приложений.
 * Сохраняется во ViewModel и переживает поворот экрана.
 */
data class AppsListUiState(
    val mainAppsList: List<AppEntity> = emptyList(),
    val showcaseAppsList: List<AppEntity> = emptyList(),
    val isLoading: Boolean = false
)

