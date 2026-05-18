package com.example.madhu_marga.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.madhu_marga.ui.screens.splash.SplashScreen
import com.example.madhu_marga.ui.screens.about.AboutScreen
import com.example.madhu_marga.ui.screens.home.HomeScreen
import com.example.madhu_marga.ui.screens.history.HistoryScreen
import com.example.madhu_marga.ui.screens.settings.SettingsScreen
import com.example.madhu_marga.ui.screens.hive_register.HiveRegisterScreen
import com.example.madhu_marga.ui.screens.inspection_log.InspectionLogScreen
import com.example.madhu_marga.ui.screens.ai_suggestions.AISuggestionsScreen
import com.example.madhu_marga.ui.screens.harvest_tracker.HarvestTrackerScreen
import com.example.madhu_marga.ui.viewmodel.AiViewModel
import com.example.madhu_marga.ui.viewmodel.HiveViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    hiveViewModel: HiveViewModel,
    aiViewModel: AiViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNavigateToNext = {
                navController.navigate(Screen.About.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.About.route) {
            AboutScreen(onNavigateToHome = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.About.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = hiveViewModel
            )
        }
        composable(Screen.History.route) {
            HistoryScreen(
                viewModel = hiveViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.HiveRegister.route) {
            HiveRegisterScreen(
                viewModel = hiveViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.InspectionLog.route) {
            InspectionLogScreen(
                viewModel = hiveViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AISuggestions.route) {
            AISuggestionsScreen(
                viewModel = aiViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.HarvestTracker.route) {
            HarvestTrackerScreen(
                viewModel = hiveViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
