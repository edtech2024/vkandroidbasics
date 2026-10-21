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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Создаем контроллер навигации
                    val navController = rememberNavController()

                    // Инициализируем граф навигации. Стартовая точка — главный список "apps_list"
                    NavHost(
                        navController = navController,
                        startDestination = "apps_list"
                    ) {
                        // ЭКРАН 1: Главный список приложений
                        composable("apps_list") {
                            AppListScreen(onAppClick = { appId ->
                                // При клике на карточку летим на экран деталей и передаем ID приложения
                                navController.navigate("app_details/$appId")
                            })
                        }

                        // ЭКРАН 2: Карточка приложения (Детали + горизонтальная «Витрина»)
                        composable(
                            route = "app_details/{appId}",
                            arguments = listOf(navArgument("appId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            // Извлекаем переданный ID приложения
                            val appId = backStackEntry.arguments?.getInt("appId") ?: 0

                            AppDetailsScreen(
                                appId = appId,
                                onBackClick = {
                                    // Клик по стрелке «Назад» в тулбаре возвращает на прошлый экран
                                    navController.popBackStack()
                                },
                                onShowcaseItemClick = {
                                    // Клик на ЛЮБОЕ приложение из блока «Витрина» возвращает на главный список
                                    navController.navigate("apps_list") {
                                        // Очищаем стек, чтобы экраны не бесконечно копились друг на друге
                                        popUpTo("apps_list") { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
