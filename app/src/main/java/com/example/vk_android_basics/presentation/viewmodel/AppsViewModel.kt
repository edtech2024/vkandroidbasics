package com.example.vk_android_basics.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkandroidbasics.domain.usecase.GetAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(
    private val getAppsUseCase: GetAppsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppsListUiState(isLoading = true))
    val uiState: StateFlow<AppsListUiState> = _uiState.asStateFlow()

    private val _effect = Channel<AppsUiEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val catalog = getAppsUseCase.getCatalog()
                _uiState.update {
                    it.copy(
                        mainAppsList = catalog,
                        showcaseAppsList = catalog.take(9),
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "Ошибка загрузки данных"
                    )
                }
            }
        }
    }

    fun loadAppDetails(appId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val app = getAppsUseCase.getAppById(appId)
                _uiState.update {
                    it.copy(
                        selectedApp = app,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "Не удалось загрузить детали приложения"
                    )
                }
            }
        }
    }

    fun onToolbarClick() {
        viewModelScope.launch {
            _effect.send(AppsUiEffect.ShowSnackbar("Клик по Тулбару обработан во ViewModel!"))
        }
    }
}
