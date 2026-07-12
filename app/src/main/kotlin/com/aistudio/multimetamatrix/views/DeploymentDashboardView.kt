package com.aistudio.multimetamatrix.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aistudio.multimetamatrix.data.AppDatabase
import com.aistudio.multimetamatrix.data.Deployment

@Composable
fun DeploymentDashboardView(database: AppDatabase) {
    val deploymentDao = database.deploymentDao()
    val deployments = deploymentDao.getAllDeployments().collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("Deployment Dashboard", style = MaterialTheme.typography.headlineMedium)
        }
        items(deployments.value) { deployment ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(deployment.name, style = MaterialTheme.typography.titleMedium)
                    LinearProgressIndicator(
                        progress = { deployment.progress },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                    Text("Health: ${deployment.health}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
