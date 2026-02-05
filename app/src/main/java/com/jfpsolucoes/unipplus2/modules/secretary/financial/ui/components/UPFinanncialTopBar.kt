package com.jfpsolucoes.unipplus2.modules.secretary.financial.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import com.jfpsolucoes.unipplus2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPFinancialTopbar(
    modifier: Modifier = Modifier,
    navigationEnabled: Boolean = true,
    onClickBack: () -> Unit,
    title: String,
    periodSelected: String?,
    openPeriodsBottomSheet: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Transparent
        ),
        navigationIcon = {
            if (navigationEnabled) {
                IconButton(
                    onClick = onClickBack
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_outline_arrow_back_24),
                        contentDescription = ""
                    )
                }
            }
        },
        title = {
            Text(text = title)
        },
        actions = {
            periodSelected?.let { period ->
                TextButton(
                    onClick = openPeriodsBottomSheet
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = period)
                        Icon(
                            modifier = Modifier,
                            painter = painterResource(R.drawable.ic_outline_arrow_drop_down_24),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    )
}