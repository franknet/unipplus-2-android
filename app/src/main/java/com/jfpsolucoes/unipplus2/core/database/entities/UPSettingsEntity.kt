package com.jfpsolucoes.unipplus2.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
data class UPSettingsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0,
    val biometricEnabled: Boolean = false,
    val biometricDialogShowed: Boolean = false
)
