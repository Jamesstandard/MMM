package com.aistudio.multimetamatrix.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deployments")
data class Deployment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val progress: Float, // 0.0 to 1.0
    val health: String // "healthy", "warning", "critical"
)
