package com.aistudio.multimetamatrix.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Artifact::class, Deployment::class, Swarm::class, Agent::class, Skill::class], version = 8, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artifactDao(): ArtifactDao
    abstract fun deploymentDao(): DeploymentDao
    abstract fun swarmDao(): SwarmDao
    abstract fun agentDao(): AgentDao
    abstract fun skillDao(): SkillDao
}
