package com.example.vk_android_basics.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkandroidbasics.domain.usecase.GetAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class AppsViewModel @Inject constructor(
    private val getAppsUseCase: GetAppsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppsListUiState(isLoading = true))
    val uiState: StateFlow<AppsListUiState> = _uiState.asStateFlow()

    private val _effect = Channel<AppsUiEffect>()
    val effect = _effect.receiveAsFlow()

    private var installJob: Job? = null

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

                // Замер времени загрузки рейтингов для 5 приложений
                val targetIds = catalog.take(12).map { it.id }
                if (targetIds.isNotEmpty()) {
                    var sequentialRatings: Map<String, Float> = emptyMap()
                    val sequentialTime = measureTimeMillis {
                        sequentialRatings = getAppsUseCase.loadRatingsSequentially(targetIds)
                    }
                    Log.d("RatingsBenchmark", "Последовательная загрузка (${targetIds.size} шт.): $sequentialTime мс")

                    var parallelRatings: Map<String, Float> = emptyMap()
                    val parallelTime = measureTimeMillis {
                        parallelRatings = getAppsUseCase.loadRatingsInParallel(targetIds)
                    }
                    Log.d("RatingsBenchmark", "Параллельная загрузка (${targetIds.size} шт.): $parallelTime мс")

                    _uiState.update { it.copy(ratings = parallelRatings) }
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
        installJob?.cancel()
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, installState = InstallState.NotInstalled) }
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

    fun onInstallClick() {
        if (installJob?.isActive == true) {
            installJob?.cancel()
            _uiState.update { it.copy(installState = InstallState.NotInstalled) }
            return
        }

        if (_uiState.value.installState is InstallState.Installed) return

        installJob = viewModelScope.launch {
            try {
                emulateInstall()
                _uiState.update { it.copy(installState = InstallState.Installed) }
            } catch (e: CancellationException) {
                _uiState.update { it.copy(installState = InstallState.NotInstalled) }
                throw e
            }
        }
    }

    private suspend fun emulateInstall() {
        for (progress in 0..100 step 5) {
            _uiState.update { it.copy(installState = InstallState.Installing(progress)) }
            delay(150)
        }
    }

    fun onToolbarClick() {
        viewModelScope.launch {
            _effect.send(AppsUiEffect.ShowSnackbar("Клик по Тулбару обработан во ViewModel!"))
        }
    }
}
