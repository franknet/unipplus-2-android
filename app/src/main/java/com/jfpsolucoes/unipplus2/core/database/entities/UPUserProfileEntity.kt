package com.jfpsolucoes.unipplus2.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile_table")
data class UPUserProfileEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0,
    @Embedded
    val user: User? = null,
    @Embedded
    val academic: Academic? = null
)

data class Academic(
    val institution: String?,
    @Embedded
    val campus: Campus?,
    @Embedded
    val course: Course?,
)

data class User(
    val rg: String?,
    @ColumnInfo("userId")
    val id: String?,
    @ColumnInfo("userName")
    val name: String?,
    val email: String?,
    val gender: String?,
    val photo: String?,
)

data class Campus(
    @ColumnInfo("campusCode")
    val code: String?,
    @ColumnInfo("campusName")
    val name: String?
)

data class Course(
    @ColumnInfo("courseCode")
    val code: String?,
    @ColumnInfo("courseName")
    val name: String?,
    val curriculum: String?,
    val educationLevel: Int?,
    val mainClass: String?,
    val shift: String?
)