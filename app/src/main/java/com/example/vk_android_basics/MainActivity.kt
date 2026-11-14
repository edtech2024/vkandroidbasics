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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
                                }
                            )
                        }

                        composable(
                            route = "app_details/{appId}",
                            arguments = listOf(navArgument("appId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val appId = backStackEntry.arguments?.getString("appId").orEmpty()

                            AppDetailsScreen(
                                appId = appId,
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onShowcaseItemClick = {
                                    navController.navigate("apps_list") {
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
