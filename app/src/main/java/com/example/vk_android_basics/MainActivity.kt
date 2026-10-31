package com.example.vk_android_basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vk_android_basics.presentation.view.AppDetailsScreen
import com.example.vk_android_basics.presentation.view.AppListScreen
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edtech2024.vkandroidbasics.data.datasource.HardcodedAppDataSource
import com.example.vk_android_basics.presentation.viewmodel.AppsViewModel
import com.example.vkandroidbasics.data.mapper.AppMapper
import com.example.vkandroidbasics.data.repository.AppRepositoryImpl
import com.example.vkandroidbasics.domain.usecase.GetAppsUseCase

class MainActivity : ComponentActivity() {

    // Сборка зависимостей Clean Architecture вручную через Factory
    private val appsViewModel: AppsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = HardcodedAppDataSource()
                val mapper = AppMapper()
                val repository = AppRepositoryImpl(dataSource, mapper)
                val useCase = GetAppsUseCase(repository)

                @Suppress("UNCHECKED_CAST")
                return AppsViewModel(useCase) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "apps_list"
                    ) {
                        composable("apps_list") {
                            AppListScreen(
                                onAppClick = { appId ->
                                    navController.navigate("app_details/$appId")
                                },
                                viewModel = appsViewModel // Прокидываем готовую ViewModel
                            )
                        }

                        composable(
                            route = "app_details/{appId}",
                            arguments = listOf(navArgument("appId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val appId = backStackEntry.arguments?.getInt("appId") ?: 0

                            AppDetailsScreen(
                                appId = appId,
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onShowcaseItemClick = {
                                    navController.navigate("apps_list") {
                                        popUpTo("apps_list") { inclusive = true }
                                    }
                                },
                                viewModel = appsViewModel // Прокидываем ту же самую ViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
