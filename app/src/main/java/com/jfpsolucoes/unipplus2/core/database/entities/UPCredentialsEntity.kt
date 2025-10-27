package com.jfpsolucoes.unipplus2.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credentials_table")
data class UPCredentialsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0,
    val rg: String = "",
    val password: String = ""
)
