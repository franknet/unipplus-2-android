package com.jfpsolucoes.unipplus2.modules.home.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystemDeeplink
import com.jfpsolucoes.unipplus2.modules.secretary.ui.UPSecretaryView

@Composable
fun UPHomeFeatureDestination(
    modifier: Modifier = Modifier,
    system: UPSystem?
) {
    if (system == null) return

    when (system.deeplink) {
        UPSystemDeeplink.SECRETARY  -> {
            UPSecretaryView()
        }
        else -> {}
    }
}