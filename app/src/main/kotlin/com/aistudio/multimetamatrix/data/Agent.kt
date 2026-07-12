package com.aistudio.multimetamatrix.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agents")
data class Agent(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val framework: String, // "crewai" | "autogen" | "openclaw" | "langgraph"
    val model: String,
    val tools: String, // comma separated list
    val provider: String,
    val baseUrl: String,
    val temperature: Float,
    val role: String,
    val description: String,
    val skills: String, // comma separated
    val isActive: Boolean = false
)
