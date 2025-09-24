package com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UPStudentRecordsDisciplinesResponse(
    val courseType: String,
    val groups: List<UPStudentRecordsDisciplinesGroup>?
): Parcelable

@Parcelize
data class UPStudentRecordsDisciplinesGroup(
    val disciplines: List<UPStudentRecordsDiscipline>?,
    val label: String?
): Parcelable

@Parcelize
data class UPStudentRecordsDiscipline(
    val absence: String?,
    val absencePercent: String?,
    val average: String?,
    val code: String?,
    val items: List<UPStudentRecordsDisciplineItem>?,
    val name: String?,
    val obs: String?,
    val type: String?
): Parcelable

@Parcelize
data class UPStudentRecordsDisciplineItem(
    val code: String?,
    val grade: String?,
    val label: String?,
    val maxGrade: String?,
    val releaseDate: String?
): Parcelable