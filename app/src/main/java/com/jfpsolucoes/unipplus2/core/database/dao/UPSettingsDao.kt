package com.jfpsolucoes.unipplus2.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UPSettingsDao {
    @Query("SELECT * FROM settings_table")
    fun getAll(): Flow<List<UPSettingsEntity>>

    @Query("SELECT * FROM settings_table WHERE id = :id")
    fun get(id: Long = 0): Flow<UPSettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: UPSettingsEntity): Long

    @Update
    suspend fun update(entity: UPSettingsEntity)

    @Delete
    suspend fun delete(entity: UPSettingsEntity)

    @Query("DELETE FROM settings_table")
    suspend fun deleteAll()
}