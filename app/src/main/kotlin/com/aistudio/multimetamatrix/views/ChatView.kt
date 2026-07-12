package com.aistudio.multimetamatrix.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aistudio.multimetamatrix.data.AppDatabase
import com.aistudio.multimetamatrix.data.Artifact
import kotlinx.coroutines.launch

@Composable
fun ChatView(database: AppDatabase) {
    val scope = rememberCoroutineScope()
    var message by remember { mutableStateOf("") }
    val artifactDao = database.artifactDao()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Box(modifier = Modifier.weight(1f)) {
            Text("Chat messages go here...")
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier.weight(1f),
                label = { Text("Ask for a project...") }
            )
            Button(onClick = {
                scope.launch {
                    // Simulate agent creation by creating a pending artifact
                    artifactDao.insertArtifact(Artifact(name = message, content = "Generating...", status = "pending"))
                    message = ""
                }
            }) {
                Text("Send")
            }
        }
    }
}
