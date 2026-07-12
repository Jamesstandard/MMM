package com.aistudio.multimetamatrix.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SwarmDao {
    @Query("SELECT * FROM swarms ORDER BY id DESC")
    fun getAllSwarms(): Flow<List<Swarm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSwarm(swarm: Swarm)
}
