@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.jfpsolucoes.unipplus2.modules.sec.academic_records.ui.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.sec.academic_records.domain.models.UPAcademicRecordsDiscipline
import com.jfpsolucoes.unipplus2.modules.sec.academic_records.domain.models.UPAcademicRecordsDisciplineInfo
import com.jfpsolucoes.unipplus2.modules.sec.academic_records.domain.models.UPAcademicRecordsSemestersGroup
import com.jfpsolucoes.unipplus2.modules.sec.academic_records.ui.UPAcademicRecordsViewModel
import com.jfpsolucoes.unipplus2.ui.components.LazyListScopeLambda
import com.jfpsolucoes.unipplus2.ui.components.discipline.UPDisciplineItemScope
import com.jfpsolucoes.unipplus2.ui.components.discipline.UPDisciplineItemView
import com.jfpsolucoes.unipplus2.ui.components.discipline.UPDisciplineSectionView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold

@Composable
fun UPAcademicRecordsDisciplinesView(
    modifier: Modifier = Modifier,
    viewModel: UPAcademicRecordsViewModel = viewModel()
) {
    val academicRecordsUIState by viewModel.academicRecordsUIState.collectAsStateWithLifecycle()

    UPUIStateScaffold(
        modifier = modifier,
        state = academicRecordsUIState,
        topBar = {
            TopAppBar(title = { Text(text = "Historico Escolar") })
        },
        loadingContent = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }
        },
        errorContent = { innerPadding, error ->
            Column(
                modifier = Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = error.localizedMessage.value)
            }
        }
    ) { innerPadding, data ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = groupsSections(innerPadding, data?.semestersGroups)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun groupsSections(padding: PaddingValues, groups: List<UPAcademicRecordsSemestersGroup>?): LazyListScopeLambda = {
    groups?.forEach { group ->
        stickyHeader(content = disciplineSection(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = padding.calculateTopPadding()),
            title = group.description.value
        )
        )
        items(
            items = group.disciplines ?: emptyList(),
            itemContent = disciplinesItem()
        )
    }
}

private fun disciplineSection(
    modifier: Modifier = Modifier,
    title: String = ""
): @Composable LazyItemScope.() -> Unit = {
    UPDisciplineSectionView(
        modifier = modifier,
        title = title
    )
}

private fun disciplinesItem(): @Composable LazyItemScope.(UPAcademicRecordsDiscipline) -> Unit = {
    UPDisciplineItemView(
        modifier = Modifier.padding(horizontal = 16.dp),
        title = it.name.value,
        content = disciplineInfosFor(it.infos)
    )
}

private fun disciplineInfosFor(infos: List<UPAcademicRecordsDisciplineInfo>?): UPDisciplineItemScope.() -> Unit = {
    infos?.forEach { info ->
        item(
            label = info.label.value,
            description = info.description.value
        )
    }
}