package com.jfpsolucoes.unipplus2.modules.home.data

import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPHomeSystemsResponse

interface UPHomeRepository {
    suspend fun getSystems(): UPHomeSystemsResponse
}