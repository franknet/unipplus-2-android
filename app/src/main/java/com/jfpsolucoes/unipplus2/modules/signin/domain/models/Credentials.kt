package com.jfpsolucoes.unipplus2.modules.signin.domain.models

import com.google.gson.annotations.SerializedName

data class Credentials(
    @SerializedName("rg") var rg: String,
    @SerializedName("password") var password: String
)
