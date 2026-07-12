package com.aistudio.multimetamatrix.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "swarms")
data class Swarm(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val framework: String, // "crewai" | "autogen" | "openclaw" | "langgraph"
    val status: String // "planning" | "running" | "review" | "completed"
)
