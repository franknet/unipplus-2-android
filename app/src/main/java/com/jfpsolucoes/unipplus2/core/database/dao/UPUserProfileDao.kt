package com.jfpsolucoes.unipplus2.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UPUserProfileDao {
    @Query("SELECT * FROM user_profile_table")
    fun getAll(): Flow<List<UPUserProfileEntity>>

    @Query("SELECT * FROM user_profile_table WHERE id = 0 LIMIT 1")
    fun get(): Flow<UPUserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: UPUserProfileEntity): Long

    @Update
    suspend fun update(entity: UPUserProfileEntity)

    @Delete
    suspend fun delete(entity: UPUserProfileEntity)

    @Query("DELETE FROM user_profile_table")
    suspend fun deleteAll()
}