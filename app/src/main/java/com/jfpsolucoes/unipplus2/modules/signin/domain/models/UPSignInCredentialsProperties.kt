package com.jfpsolucoes.unipplus2.modules.signin.domain.models

data class UPSignInCredentialsProperties(
    var idText: String,
    var onEditId: (String) -> Unit = {},
    var passwordText: String,
    var onEditPassword: (String) -> Unit = {},
    var isLoading: Boolean = false,
    var contentWidthFraction: Float = 1f,
    var onClickSignIn: () -> Unit = {}
)