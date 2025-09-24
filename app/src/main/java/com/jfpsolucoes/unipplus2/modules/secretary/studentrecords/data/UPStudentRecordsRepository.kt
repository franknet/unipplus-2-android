package com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.data

import com.jfpsolucoes.unipplus2.modules.secretary.studentrecords.domain.models.UPStudentRecordsDisciplinesResponse

interface UPStudentRecordsRepository {

    suspend fun getDisciplines(): UPStudentRecordsDisciplinesResponse

}