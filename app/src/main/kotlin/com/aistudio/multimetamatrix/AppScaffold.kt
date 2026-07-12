package com.aistudio.multimetamatrix

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import com.aistudio.multimetamatrix.screens.HomeScreen
import com.aistudio.multimetamatrix.views.ArtifactsView
import com.aistudio.multimetamatrix.views.ChatView
import com.aistudio.multimetamatrix.views.AgentBuilderView
import com.aistudio.multimetamatrix.views.AgentListView
import com.aistudio.multimetamatrix.views.SkillsView
import com.aistudio.multimetamatrix.views.DeploymentDashboardView
import com.aistudio.multimetamatrix.views.SwarmsView
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.unit.dp
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import com.aistudio.multimetamatrix.data.AppPreferences

@Composable
fun AppScaffold(database: AppDatabase, appPreferences: AppPreferences) {
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
        NavigationItem("deployments", "Deployments", Icons.Default.RocketLaunch),
        NavigationItem("builder", "Builder", Icons.Default.Build),
        NavigationItem("agents", "Agents", Icons.Default.List)
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
                    composable("swarms") { SwarmsView(database) }
                    composable("skills") { SkillsView(database) }
                    composable("memory") { Text("Memory Screen") }
                    composable("inspect") { Text("Inspect Screen") }
                    composable("artifacts") { ArtifactsView(database) }
                    composable("deployments") { DeploymentDashboardView(database) }
                    composable("builder") { AgentBuilderView(database) }
                    composable("agents") { AgentListView(database) }
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
        val context = androidx.compose.ui.platform.LocalContext.current
        ModalBottomSheet(onDismissRequest = { showSettings = false }) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Settings Panel", style = MaterialTheme.typography.titleLarge)
                
                val token by appPreferences.githubToken.collectAsState(initial = "")
                val connected by appPreferences.githubConnected.collectAsState(initial = false)
                val autoSync by appPreferences.autoSync.collectAsState(initial = false)
                val scope = rememberCoroutineScope()
                
                OutlinedTextField(
                    value = token ?: "",
                    onValueChange = { scope.launch { appPreferences.updateGithubToken(it) } },
                    label = { Text("GitHub Token") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = { 
                    scope.launch { appPreferences.updateGithubConnected(token?.isNotEmpty() == true) }
                }) {
                    Text("Save Connection")
                }
                
                if (connected) {
                    Text("Status: Connected", color = MaterialTheme.colorScheme.primary)
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("Auto-Sync")
                        androidx.compose.material3.Switch(
                            checked = autoSync,
                            onCheckedChange = { scope.launch { appPreferences.updateAutoSync(it) } }
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { 
                            val service = com.aistudio.multimetamatrix.data.GitHubSyncService(database, appPreferences)
                            scope.launch { service.pushData() }
                        }) {
                            Text("Push")
                        }
                        Button(onClick = { 
                            val service = com.aistudio.multimetamatrix.data.GitHubSyncService(database, appPreferences)
                            scope.launch { service.pullData() }
                        }) {
                            Text("Pull")
                        }
                    }
                } else {
                    Text("Status: Disconnected", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
    
    // Auto-sync observer
    val autoSyncEnabled by appPreferences.autoSync.collectAsState(initial = false)
    val swarms by database.swarmDao().getAllSwarms().collectAsState(initial = emptyList())
    
    LaunchedEffect(autoSyncEnabled, swarms) {
        if (autoSyncEnabled && swarms.isNotEmpty()) {
             // Basic implementation: push whenever swarms change
             val service = com.aistudio.multimetamatrix.data.GitHubSyncService(database, appPreferences)
             service.pushData()
        }
    }
}

data class NavigationItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
