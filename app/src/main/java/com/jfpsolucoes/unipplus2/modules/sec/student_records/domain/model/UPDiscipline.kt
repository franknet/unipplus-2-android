package com.jfpsolucoes.unipplus2.modules.sec.student_records.domain.model

data class UPDiscipline(
    val code: String? = null,
    val name: String? = null,
    val type: String? = null,
    val classCode: String? = null,
    val classesPlanned: Int? = null,
    val classesAttended: Int? = null,
    val tests: List<UPTest>? = null,
    val absence: List<UPAbsence>? = null,
)