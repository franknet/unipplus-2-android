package com.jfpsolucoes.unipplus2.modules.studentprofile.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UPStudentProfileView(
    modifier: Modifier = Modifier,
    viewModel: UPStudentProfileViewModel = viewModel(UPStudentProfileViewModel::class.java)
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Student Profile")
    }
}