package com.jfpsolucoes.unipplus2.modules.sec.student_records.domain.model

data class UPDisciplineTestHistogram(
    val maxScore: Int? = null,
    val highestScore: Int? = null,
    val lowestScore: Int? = null,
    val standardDeviation: Double?  = null,
    val histogram: List<UPDisciplineTestHistogramItem>?  = null
)