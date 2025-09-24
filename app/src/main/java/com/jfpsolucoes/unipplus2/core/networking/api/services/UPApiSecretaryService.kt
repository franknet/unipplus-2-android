package com.jfpsolucoes.unipplus2.core.networking.api.services

import com.jfpsolucoes.unipplus2.core.networking.api.UPApiEndpoints
import retrofit2.Response
import retrofit2.http.GET

interface UPApiSecretaryService {
    @GET(UPApiEndpoints.Secretary.FEATURES)
    suspend fun getFeatures(): Response<String>

    interface StudentRecords {
        @GET(UPApiEndpoints.Secretary.StudentRecords.DISCIPLINES)
        suspend fun getDisciplines(): Response<String>
    }

    interface Financial {

    }

}