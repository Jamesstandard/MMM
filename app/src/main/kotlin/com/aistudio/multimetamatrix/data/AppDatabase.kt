package com.aistudio.multimetamatrix.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Artifact::class, Deployment::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artifactDao(): ArtifactDao
    abstract fun deploymentDao(): DeploymentDao
}
