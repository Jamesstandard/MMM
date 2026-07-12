package com.aistudio.multimetamatrix.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtifactDao {
    @Query("SELECT * FROM artifacts ORDER BY id DESC")
    fun getAllArtifacts(): Flow<List<Artifact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtifact(artifact: Artifact)

    @Query("DELETE FROM artifacts WHERE id = :id")
    suspend fun deleteArtifactById(id: Int)
}
