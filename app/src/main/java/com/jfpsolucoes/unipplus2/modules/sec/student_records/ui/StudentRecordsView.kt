
package com.jfpsolucoes.unipplus2.modules.sec.student_records.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.sec.student_records.domain.model.UPDiscipline
import com.jfpsolucoes.unipplus2.modules.sec.student_records.domain.model.UPDisciplinesGroup
import com.jfpsolucoes.unipplus2.modules.sec.student_records.domain.model.UPTest
import com.jfpsolucoes.unipplus2.ui.components.LazyListScopeLambda
import com.jfpsolucoes.unipplus2.ui.components.discipline.UPDisciplineItemScope
import com.jfpsolucoes.unipplus2.ui.components.discipline.UPDisciplineItemView
import com.jfpsolucoes.unipplus2.ui.components.discipline.UPDisciplineSectionView
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StudentRecordsView(
    modifier: Modifier = Modifier,
    viewModel: StudentRecordsViewModel = viewModel(StudentRecordsViewModel::class)
) {
    val recordsUIState by viewModel.studentRecords.collectAsState()
    UPUIStateScaffold(
        modifier = modifier.fillMaxSize(),
        state = recordsUIState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Notas e Faltas") }
            )
        },
        loadingContent = { },
        errorContent = { _, error -> }
    ) { innerPadding, data ->
        LazyColumn(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = groupsSections(data?.disciplinesGroups)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun groupsSections(groups: List<UPDisciplinesGroup>?): LazyListScopeLambda = {
    groups?.forEach { group ->
        stickyHeader(content = disciplineSection(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            title = group.description.value
        ))
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

private fun disciplinesItem(): @Composable LazyItemScope.(UPDiscipline) -> Unit = {
    UPDisciplineItemView(
        modifier = Modifier.padding(horizontal = 16.dp),
        title = it.name.value,
        content = testFor(it.tests)
    )
}

private fun testFor(tests: List<UPTest>?): UPDisciplineItemScope.() -> Unit = {
    tests?.forEach {
        item(
            label = it.code.value,
            description = it.grade.value,
            fraction = it.gradeFraction ?: 0f
        )
    }
}
