package com.jfpsolucoes.unipplus2.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jfpsolucoes.unipplus2.core.database.entities.UPNetworkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UPNetworkDao {
    @Query("SELECT * FROM network_table WHERE id= :id LIMIT 1")
    fun get(id: Long = 0): Flow<UPNetworkEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: UPNetworkEntity): Long
}