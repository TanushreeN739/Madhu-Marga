package com.example.madhu_marga.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.madhu_marga.ui.navigation.Screen
import com.example.madhu_marga.ui.theme.Amber
import com.example.madhu_marga.ui.theme.HoneyYellow
import com.example.madhu_marga.ui.viewmodel.HiveViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HiveViewModel) {
    val hives by viewModel.allHives.collectAsStateWithLifecycle()
    val harvests by viewModel.allHarvests.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = { HomeBottomNavigation(navController, currentRoute = Screen.Home.route) },
        topBar = {
            TopAppBar(
                title = { Text("Madhu-Marga", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HoneyYellow,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            WelcomeCard(hives.size, harvests.sumOf { it.amount })
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            val dashboardItems = listOf(
                DashboardItem("Hive Register", Icons.Default.Hive, Screen.HiveRegister.route, Amber),
                DashboardItem("Inspection Log", Icons.Default.Assignment, Screen.InspectionLog.route, Color(0xFF8BC34A)),
                DashboardItem("AI Suggestions", Icons.Default.AutoAwesome, Screen.AISuggestions.route, Color(0xFF03A9F4)),
                DashboardItem("Harvest Tracker", Icons.Default.WaterDrop, Screen.HarvestTracker.route, Color(0xFFFF9800))
            )
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(dashboardItems) { item ->
                    DashboardCard(item) {
                        navController.navigate(item.route)
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeCard(hiveCount: Int, totalHarvest: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Welcome Back, Keeper! 🐝",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "You have $hiveCount active hives. Total honey: ${String.format(Locale.getDefault(), "%.1f", totalHarvest)} kg.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                progress = { 0.7f },
                modifier = Modifier.fillMaxWidth(),
                color = HoneyYellow,
                trackColor = HoneyYellow.copy(alpha = 0.2f),
            )
        }
    }
}

@Composable
fun DashboardCard(item: DashboardItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    modifier = Modifier.size(64.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = item.color.copy(alpha = 0.15f)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp),
                        tint = item.color
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun HomeBottomNavigation(navController: NavController, currentRoute: String?) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == Screen.Home.route,
            onClick = {
                if (currentRoute != Screen.Home.route) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = HoneyYellow,
                selectedTextColor = HoneyYellow,
                indicatorColor = HoneyYellow.copy(alpha = 0.1f),
                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.History, contentDescription = "History") },
            label = { Text("History") },
            selected = currentRoute == Screen.History.route,
            onClick = {
                if (currentRoute != Screen.History.route) {
                    navController.navigate(Screen.History.route)
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = HoneyYellow,
                selectedTextColor = HoneyYellow,
                indicatorColor = HoneyYellow.copy(alpha = 0.1f),
                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = currentRoute == Screen.Settings.route,
            onClick = {
                if (currentRoute != Screen.Settings.route) {
                    navController.navigate(Screen.Settings.route)
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = HoneyYellow,
                selectedTextColor = HoneyYellow,
                indicatorColor = HoneyYellow.copy(alpha = 0.1f),
                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        )
    }
}

data class DashboardItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val color: Color
)
