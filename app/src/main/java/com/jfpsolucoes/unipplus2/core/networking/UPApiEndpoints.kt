package com.jfpsolucoes.unipplus2.core.networking

import com.jfpsolucoes.unipplus2.BuildConfig

object UPApiEndpoints {
    object Auth {
        const val SIGN_IN = BuildConfig.API_LOCATION + "/auth/v1/sign-in"
        const val SYSTEMS = BuildConfig.API_LOCATION + "/auth/v1/systems"
    }

    object Secretary {
        const val FEATURES = BuildConfig.API_LOCATION + "/secretary/v1/features"
        const val STUDENT_RECORDS_DISCIPLINES = BuildConfig.API_LOCATION + "/secretary/student-records/disciplines"
        const val FINANCE = BuildConfig.API_LOCATION + "/secretary/signin"
    }
}