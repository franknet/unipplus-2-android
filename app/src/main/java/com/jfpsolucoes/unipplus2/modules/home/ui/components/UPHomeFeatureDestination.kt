package com.jfpsolucoes.unipplus2.modules.home.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystem
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystemDeeplink
import com.jfpsolucoes.unipplus2.modules.home.domain.models.UPSystemType
import com.jfpsolucoes.unipplus2.ui.components.web.PortalWebView

@Composable
fun UPHomeFeatureDestination(
    modifier: Modifier = Modifier,
    system: UPSystem?
) {
    if (system == null) return

    if (system.type == UPSystemType.FEATURE) {
        when (system.deeplink) {
            UPSystemDeeplink.SECRETARY  -> {}
            else -> {}
        }
    } else {
        PortalWebView(
            modifier = modifier,
            url = system.url.value
        )
    }
}