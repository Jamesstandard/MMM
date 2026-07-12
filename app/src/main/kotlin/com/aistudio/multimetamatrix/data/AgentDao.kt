package com.aistudio.multimetamatrix.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentDao {
    @Query("SELECT * FROM agents ORDER BY id DESC")
    fun getAllAgents(): Flow<List<Agent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAgent(agent: Agent)

    @androidx.room.Update
    suspend fun updateAgent(agent: Agent)
}
