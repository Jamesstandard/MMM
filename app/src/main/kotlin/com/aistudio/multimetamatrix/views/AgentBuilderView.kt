package com.aistudio.multimetamatrix.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.aistudio.multimetamatrix.data.Agent
import com.aistudio.multimetamatrix.data.AppDatabase
import kotlinx.coroutines.launch

@Composable
fun AgentBuilderView(database: AppDatabase) {
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var framework by remember { mutableStateOf("langgraph") }
    var model by remember { mutableStateOf("gemini-1.5-flash") }
    var tools by remember { mutableStateOf("") }
    var provider by remember { mutableStateOf("google") }
    var baseUrl by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("0.7") }
    var role by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }
    var selectedPreset by remember { mutableStateOf("Default") }
    val presets = listOf("Default", "Data Analyst", "Code Generator")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Agent Builder", style = MaterialTheme.typography.headlineMedium)
        
        // Preset selection
        Text("Select Preset:")
        presets.forEach { preset ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = selectedPreset == preset, onClick = { 
                    selectedPreset = preset
                    if (preset == "Data Analyst") {
                        model = "gemini-1.5-pro"
                        tools = "pandas, numpy"
                    }
                })
                Text(preset)
            }
        }
        
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Agent Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = framework, onValueChange = { framework = it }, label = { Text("Framework") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Model") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tools, onValueChange = { tools = it }, label = { Text("Tools (comma separated)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = provider, onValueChange = { provider = it }, label = { Text("Provider") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = baseUrl, onValueChange = { baseUrl = it }, label = { Text("Base URL") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = temperature, onValueChange = { temperature = it }, label = { Text("Temperature") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = role, onValueChange = { role = it }, label = { Text("Role") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = skills, onValueChange = { skills = it }, label = { Text("Skills (comma separated)") }, modifier = Modifier.fillMaxWidth())
        
        Button(onClick = {
            scope.launch {
                database.agentDao().insertAgent(Agent(
                    name = name, 
                    framework = framework, 
                    model = model, 
                    tools = tools,
                    provider = provider,
                    baseUrl = baseUrl,
                    temperature = temperature.toFloatOrNull() ?: 0.7f,
                    role = role,
                    description = description,
                    skills = skills
                ))
                name = ""
                tools = ""
                role = ""
                description = ""
                skills = ""
            }
        }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Create Agent")
        }
    }
}
