package com.aistudio.multimetamatrix

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import com.aistudio.multimetamatrix.screens.HomeScreen
import com.aistudio.multimetamatrix.views.ArtifactsView
import com.aistudio.multimetamatrix.views.ChatView
import com.aistudio.multimetamatrix.views.DeploymentDashboardView
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.ui.unit.dp
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.PuzzlePiece
import androidx.compose.material.icons.filled.Brain
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState

import com.aistudio.multimetamatrix.data.AppDatabase

@Composable
fun AppScaffold(database: AppDatabase) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"
    var showSettings by remember { mutableStateOf(false) }

    val items = listOf(
        NavigationItem("home", "Home", Icons.Default.Home),
        NavigationItem("chat", "Chat", Icons.Default.Chat),
        NavigationItem("swarms", "Swarms", Icons.Default.GridView),
        NavigationItem("skills", "Skills", Icons.Default.PuzzlePiece),
        NavigationItem("memory", "Memory", Icons.Default.Brain),
        NavigationItem("inspect", "Inspect", Icons.Default.BugReport),
        NavigationItem("artifacts", "Artifacts", Icons.Default.Inventory),
        NavigationItem("deployments", "Deployments", Icons.Default.RocketLaunch)
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            items.forEach { item ->
                NavigationSuiteItem(
                    selected = currentRoute == item.route,
                    onClick = { navController.navigate(item.route) },
                    icon = { Icon(item.icon, contentDescription = item.label) },
                    label = { Text(item.label) }
                )
            }
            // Add settings as a navigation item too for ease or as a separate action
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { 
                    HomeScreen(onNewObjectiveClick = { navController.navigate("chat") })
                }
                    composable("chat") { ChatView(database) }
                    composable("swarms") { Text("Swarms Screen") }
                    composable("skills") { Text("Skills Screen") }
                    composable("memory") { Text("Memory Screen") }
                    composable("inspect") { Text("Inspect Screen") }
                    composable("artifacts") { ArtifactsView(database) }
                    composable("deployments") { DeploymentDashboardView(database) }
                }
                
                // Settings trigger (positioned on top right edge)
                IconButton(
                    onClick = { showSettings = true },
                    modifier = Modifier
                        .align(androidx.compose.ui.Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        }
    }
    
    if (showSettings) {
        ModalBottomSheet(onDismissRequest = { showSettings = false }) {
            Text("Settings Panel")
        }
    }
}

data class NavigationItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
