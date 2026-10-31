package com.example.vk_android_basics.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.example.vkandroidbasics.domain.usecase.GetAppsUseCase

class AppsViewModel(private val getAppsUseCase: GetAppsUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(AppsListUiState())
    val uiState: StateFlow<AppsListUiState> = _uiState.asStateFlow()

    private val _effect = Channel<AppsUiEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.value = AppsListUiState(
            mainAppsList = getAppsUseCase.getMainList(),
            showcaseAppsList = getAppsUseCase.getShowcaseList(),
            isLoading = false
        )
    }

    fun onToolbarClick() {
        viewModelScope.launch {
            _effect.send(AppsUiEffect.ShowSnackbar("Клик по Тулбару обработан во ViewModel!"))
        }
    }
}
