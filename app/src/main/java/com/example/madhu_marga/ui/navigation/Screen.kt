package com.example.madhu_marga.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object About : Screen("about_screen")
    object Home : Screen("home_screen")
    object History : Screen("history_screen")
    object Settings : Screen("settings_screen")
    object HiveRegister : Screen("hive_register_screen")
    object InspectionLog : Screen("inspection_log_screen")
    object AISuggestions : Screen("ai_suggestions_screen")
    object HarvestTracker : Screen("harvest_tracker_screen")
}
