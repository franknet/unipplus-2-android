package com.jfpsolucoes.unipplus2.modules.profile.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.common.model.UPAppInfo
import com.jfpsolucoes.unipplus2.core.database.SHARED_KEY_APP_INFO
import com.jfpsolucoes.unipplus2.core.database.SharedPreferencesManager
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.ui.components.spacer.HorizontalSpacer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UPProfileView(
    modifier: Modifier = Modifier,
    navigationIconEnabled: Boolean = true,
    onBackPressed: () -> Unit = {},
) {
    val session = SharedPreferencesManager.getObject<UPAppInfo>(SHARED_KEY_APP_INFO)?.session

    BackHandler {
        onBackPressed()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {  }, navigationIcon = {
                if (navigationIconEnabled) {
                    IconButton(onClick = onBackPressed) {
                        Icon(painter = painterResource(R.drawable.ic_outline_arrow_back_24), contentDescription = "")
                    }
                }
            })
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp,
                    bottom = padding.calculateBottomPadding()
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(R.drawable.ic_outline_account_circle_24),
                        contentDescription = ""
                    )

                    Text(session?.user?.rg.value, style = MaterialTheme.typography.titleSmall)

                    Text(session?.user?.name.value, style = MaterialTheme.typography.titleMedium)
                }

            }

            item {
                UPProfileInfoCard(title = "Campus") {
                    session?.academic?.institution?.let {
                        UPProfileInfoRow(
                            "Instituição:",
                            it
                        )
                    }

                    session?.academic?.campus?.name?.let {
                        UPProfileInfoRow(
                            "Unidade:",
                            it
                        )
                    }
                }
            }

            item {
                UPProfileInfoCard(title = "Curso") {
                    Text(
                        session?.academic?.course?.name.value,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    HorizontalDivider()

                    session?.academic?.course?.shift?.let {
                        UPProfileInfoRow(
                            "Turno:",
                            it
                        )
                    }

                    session?.academic?.course?.mainClass?.let {
                        UPProfileInfoRow(
                            "Turma:",
                            it
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UPProfileInfoCard(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable (ColumnScope) -> Unit
) {
    Column {
        Text(title, style = MaterialTheme.typography.titleLarge)

        Card {
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                content = content
            )
        }
    }
}

@Composable
fun UPProfileInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium
        )

        HorizontalSpacer()

        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun UPProfileViewPreview() {
    UPProfileView()
}