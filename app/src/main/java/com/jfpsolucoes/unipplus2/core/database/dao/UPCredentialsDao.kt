package com.jfpsolucoes.unipplus2.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jfpsolucoes.unipplus2.core.database.entities.UPCredentialsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UPCredentialsDao {
    @Query("SELECT * FROM credentials_table")
    fun getAll(): Flow<List<UPCredentialsEntity>>

    @Query("SELECT * FROM credentials_table WHERE id= :id LIMIT 1")
    fun get(id: Long = 0): Flow<UPCredentialsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: UPCredentialsEntity): Long

    @Update
    suspend fun update(entity: UPCredentialsEntity)

    @Delete
    suspend fun delete(entity: UPCredentialsEntity)

    @Query("DELETE FROM credentials_table")
    suspend fun deleteAll()
}