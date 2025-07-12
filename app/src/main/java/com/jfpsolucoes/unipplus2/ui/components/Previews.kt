package com.jfpsolucoes.unipplus2.ui.components

import androidx.compose.ui.tooling.preview.Preview


@Preview(
    name = "Phone Portrait",
    showBackground = true,
    device = "spec:width=411dp,height=891dp"
)

@Preview(
    name = "Phone Landscape",
    showBackground = true,
    device = "spec:width=411dp,height=891dp,orientation=landscape"
)

@Preview(
    name = "Tablet Portrait",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait"
)

@Preview(
    name = "Tablet Landscape",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
annotation class DevicesPreview