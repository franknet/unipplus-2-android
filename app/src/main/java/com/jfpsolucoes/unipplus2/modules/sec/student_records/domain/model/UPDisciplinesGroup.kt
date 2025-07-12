package com.jfpsolucoes.unipplus2.modules.sec.student_records.domain.model

data class UPDisciplinesGroup(
    val type: String? = null,
    val description: String? = null,
    val disciplines: List<UPDiscipline>? = null

)
