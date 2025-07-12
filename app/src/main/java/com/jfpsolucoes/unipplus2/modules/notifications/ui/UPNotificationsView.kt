package com.jfpsolucoes.unipplus2.modules.notifications.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfpsolucoes.unipplus2.core.utils.extensions.toAnnotatedString
import com.jfpsolucoes.unipplus2.ui.components.layout.UPUIStateScaffold

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UPNotificationsView(
    modifier: Modifier = Modifier,
    viewModel: UPNotificationsViewModel = viewModel()
) {
    val notificationsUIState by viewModel.notificationsUIState.collectAsState()

    UPUIStateScaffold(
        modifier = modifier
            .safeDrawingPadding()
            .fillMaxSize(),
        state = notificationsUIState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Avisos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        loadingContent = {},
        errorContent = { innerPagging, error ->

        }
    ) { innerPadding, data ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            data?.let {
                items(it) { notification ->
                    val annotatedString = notification.description.toAnnotatedString(LocalContext.current)
                    Card(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = annotatedString.toString(),
                        )
                    }
                }
            }
            item {  }

        }
    }
}