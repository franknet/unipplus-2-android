package com.jfpsolucoes.unipplus2.core.common.model

import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPSignInResponse

data class UPAppInfo(
    var session: UPSignInResponse?,
    var isKeepCredentialsEnabled: Boolean = false,
    var selectedTheme: UPAppTheme = UPAppTheme.SYSTEM
)