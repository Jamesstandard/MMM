package com.aistudio.multimetamatrix.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aistudio.multimetamatrix.data.AppDatabase
import kotlinx.coroutines.launch

@Composable
fun AgentListView(database: AppDatabase) {
    val agentDao = database.agentDao()
    val agents = agentDao.getAllAgents().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("Your Agents", style = MaterialTheme.typography.headlineMedium)
        }
        items(agents.value) { agent ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(agent.name, style = MaterialTheme.typography.titleMedium)
                        if (agent.isActive) {
                            Badge(containerColor = MaterialTheme.colorScheme.primaryContainer) { Text("Active") }
                        }
                    }
                    Text("Role: ${agent.role}", style = MaterialTheme.typography.bodySmall)
                    Text("Description: ${agent.description}", style = MaterialTheme.typography.bodySmall)
                    Text("LLM: ${agent.model} (${agent.provider})", style = MaterialTheme.typography.bodySmall)
                    Text("Skills: ${agent.skills}", style = MaterialTheme.typography.bodySmall)
                    IconButton(onClick = { 
                        scope.launch {
                            agentDao.updateAgent(agent.copy(isActive = !agent.isActive))
                        }
                    }) {
                        Icon(if (agent.isActive) Icons.Default.Stop else Icons.Default.PlayArrow, contentDescription = "Toggle Activation")
                    }
                }
            }
        }
    }
}
