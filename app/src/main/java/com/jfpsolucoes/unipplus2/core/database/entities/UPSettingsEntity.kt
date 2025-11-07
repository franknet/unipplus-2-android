package com.jfpsolucoes.unipplus2.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
data class UPSettingsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0,

    // Security
    val biometricEnabled: Boolean = false,
    val biometricDialogEnabled: Boolean = true,

    // App
    val appRateCount: Int = 0
)
