package com.aistudio.multimetamatrix.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aistudio.multimetamatrix.data.AppDatabase
import com.aistudio.multimetamatrix.data.Swarm

@Composable
fun SwarmsView(database: AppDatabase) {
    val swarmDao = database.swarmDao()
    val swarms = swarmDao.getAllSwarms().collectAsState(initial = emptyList())
    val statuses = listOf("planning", "running", "review", "completed")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Swarms (Kanban)", style = MaterialTheme.typography.headlineMedium)
        
        // Kanban implementation (simplistic for now)
        Row(modifier = Modifier.fillMaxWidth()) {
            statuses.forEach { status ->
                Column(modifier = Modifier.weight(1f).padding(4.dp)) {
                    Text(status.replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.titleSmall)
                    
                    val filteredSwarms = swarms.value.filter { it.status == status }
                    
                    LazyColumn {
                        items(filteredSwarms) { swarm ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(swarm.name, style = MaterialTheme.typography.bodyLarge)
                                    Text(swarm.framework, style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
