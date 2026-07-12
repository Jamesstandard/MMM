package com.aistudio.multimetamatrix.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DeploymentDao {
    @Query("SELECT * FROM deployments ORDER BY id DESC")
    fun getAllDeployments(): Flow<List<Deployment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeployment(deployment: Deployment)
}
