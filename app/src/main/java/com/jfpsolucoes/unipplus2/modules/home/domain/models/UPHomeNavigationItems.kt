package com.jfpsolucoes.unipplus2.modules.home.domain.models

import com.jfpsolucoes.unipplus2.R

data class UPHomeNavigationItem(
    val labelId: Int,
    val iconId: Int
)

val UPHomeNavigationItems = listOf(
    UPHomeNavigationItem(labelId = R.string.home_navigation_item_home, iconId = R.drawable.ic_outline_home_24),
    UPHomeNavigationItem(labelId = R.string.home_navigation_item_settings, iconId = R.drawable.ic_outline_services_24),
)