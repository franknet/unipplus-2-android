package com.jfpsolucoes.unipplus2.modules.signin.domain.models

import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("name") val name: String?,
    @SerializedName("course") val course: StudentCourse?
)
