package com.jfpsolucoes.unipplus2.modules.sec.academic_records.domain.models

data class UPAcademicRecordsResponse(
    val semestersGroups: List<UPAcademicRecordsSemestersGroup>? = null,
)

data class UPAcademicRecordsSemestersGroup(
    val description: String? = null,
    val disciplines: List<UPAcademicRecordsDiscipline>? = null,
)

data class UPAcademicRecordsDiscipline(
    val semester: String? = null,
    val code: String? = null,
    val name: String? = null,
    val infos: List<UPAcademicRecordsDisciplineInfo>? = null,
    val status: UPAcademicRecordsDisciplineStatus? = null,
    val showMessage: Boolean = false,
    val message: String? = null
)

data class UPAcademicRecordsDisciplineStatus(
    val description: String? = null,
)

data class UPAcademicRecordsDisciplineInfo(
    val label: String? = null,
    val description: String? = null,
)