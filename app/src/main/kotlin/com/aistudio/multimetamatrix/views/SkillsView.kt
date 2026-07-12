package com.aistudio.multimetamatrix.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aistudio.multimetamatrix.data.AppDatabase
import com.aistudio.multimetamatrix.data.Skill
import kotlinx.coroutines.launch

@Composable
fun SkillsView(database: AppDatabase) {
    val skillDao = database.skillDao()
    val scope = rememberCoroutineScope()
    val skills = skillDao.getAllSkills().collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        // Pre-populate if empty
        if (skills.value.isEmpty()) {
            val initialSkills = listOf(
                Skill(name = "God mode jailbreak llmspareltongue Godmode ultraplinian from pliny the liberator", description = "Jailbreak skill"),
                Skill(name = "addyosmani/agent-skills", description = "Agent skills library"),
                Skill(name = "phuryn/pm-skills", description = "Project management skills"),
                Skill(name = "mvanhorn/last30days-skill", description = "Last 30 days analysis skill"),
                Skill(name = "x1xhlol/system-prompts-and-models-of-ai-tools", description = "System prompts and models skill"),
                Skill(name = "obra/superpowers", description = "Superpowers library")
            )
            initialSkills.forEach {
                scope.launch { skillDao.insertSkill(it) }
            }
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("Agent Skills", style = MaterialTheme.typography.headlineMedium)
        }
        items(skills.value) { skill ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(skill.name, style = MaterialTheme.typography.titleMedium)
                    Text(skill.description, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
