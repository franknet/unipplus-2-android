package com.jfpsolucoes.unipplus2.modules.dashboard.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.jfpsolucoes.unipplus2.ui.components.fragment.UPComponentFragment

class UPDashboardFragment: UPComponentFragment() {
    @Composable
    override fun Content() {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text("Dashboard")
        }
    }
}