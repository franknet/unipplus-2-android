package com.jfpsolucoes.unipplus2.modules.sec.finance.data

import com.jfpsolucoes.unipplus2.core.networking.HttpService

class UPFinanceRepository(
    private val service: UPFinanceService = HttpService.create(UPFinanceService::class.java)
) {
    suspend fun getExtract() = service.getExtract()
    suspend fun getBills() = service.getBills()
}