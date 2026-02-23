package com.jfpsolucoes.unipplus2.modules.secretary.financial.usecases

import androidx.core.net.toUri
import com.jfpsolucoes.unipplus2.core.file.UPFileDirectoryManager
import com.jfpsolucoes.unipplus2.core.utils.extensions.toUIStateFlow
import com.jfpsolucoes.unipplus2.modules.secretary.features.data.UPSecretaryRepository
import com.jfpsolucoes.unipplus2.modules.secretary.features.domain.repository.UPSecretaryRepositoryImpl
import com.jfpsolucoes.unipplus2.modules.secretary.financial.data.UPFinancialRepository
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.models.UPFinancialFeaturesData
import com.jfpsolucoes.unipplus2.modules.secretary.financial.domain.repository.UPFinancialRepositoryImpl
import com.jfpsolucoes.unipplus2.ui.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URLDecoder

class UPGetFinancialDownloadUseCase(
    val repository: UPFinancialRepository = UPFinancialRepositoryImpl(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(path: String) = flow {
        val uri = path.toUri()
        val bankSlipCode = uri.getQueryParameter("bankSlipCode").orEmpty()
        val data = withContext(dispatcher) {
            repository.download(uri.path.orEmpty(), bankSlipCode)
        }
        val cacheDir = UPFileDirectoryManager.cacheDirectory
        val file = File.createTempFile("boleto_$bankSlipCode", ".pdf", cacheDir)
        val input = data.byteStream()
        val output = file.outputStream()
        input.copyTo(output)
        output.flush()
        emit(file)
    }.toUIStateFlow()
}
