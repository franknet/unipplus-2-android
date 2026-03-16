package com.jfpsolucoes.unipplus2.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "network_table")
data class UPNetworkEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val uuid: String = "",
    val userId: String = "",
    val userRg: String = "",
    val courseType: String = "",
    val accessToken: String = "",
    val refreshToken: String = "",
)
