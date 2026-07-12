package com.aistudio.multimetamatrix.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artifacts")
data class Artifact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val content: String,
    val status: String // "pending" | "complete"
)
