package com.aistudio.multimetamatrix.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aistudio.multimetamatrix.data.AppDatabase
import com.aistudio.multimetamatrix.data.Artifact
import kotlinx.coroutines.flow.collect

@Composable
fun ArtifactsView(database: AppDatabase) {
    val artifactDao = database.artifactDao()
    val artifacts = artifactDao.getAllArtifacts().collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("Artifacts", style = MaterialTheme.typography.headlineMedium)
        }
        items(artifacts.value) { artifact ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(artifact.name, style = MaterialTheme.typography.titleMedium)
                    Text(artifact.status, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
