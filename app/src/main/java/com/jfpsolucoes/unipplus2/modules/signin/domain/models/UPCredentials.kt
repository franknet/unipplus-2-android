package com.jfpsolucoes.unipplus2.modules.signin.domain.models

import com.google.gson.annotations.SerializedName

data class UPCredentials(
    @SerializedName("rg") var rg: String,
    @SerializedName("password") var password: String
)
