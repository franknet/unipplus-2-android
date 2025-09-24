package com.jfpsolucoes.unipplus2.core.networking.api

import com.jfpsolucoes.unipplus2.BuildConfig

object UPApiEndpoints {
    object Auth {
        const val SIGN_IN = BuildConfig.API_AUTH + "/v1/sign-in"
        const val SYSTEMS = BuildConfig.API_AUTH + "/v1/systems"
    }

    object Secretary {
        const val FEATURES = BuildConfig.API_SECRETARY + "/v1/features"

        object StudentRecords {
            const val DISCIPLINES = BuildConfig.API_SECRETARY + "/student-records/v1/disciplines"
        }

        object Finance {

        }

    }
}