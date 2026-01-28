package com.jfpsolucoes.unipplus2.core.networking.api

import com.jfpsolucoes.unipplus2.BuildConfig

object UPApiEndpoints {
    const val BASE_URL = BuildConfig.BASE_URL

    object Auth {
        private const val AUTH_API = BuildConfig.API_AUTH
        const val SIGN_IN = "$AUTH_API/v1/sign-in"
        const val SYSTEMS = "$AUTH_API/v1/systems"
    }

    object Secretary {
        private const val SECRETARY_API = BuildConfig.API_SECRETARY
        const val FEATURES = "$SECRETARY_API/v1/features"

        object StudentRecords {
            private const val STUDENT_RECORDS_API = "$SECRETARY_API/student-records"
            const val DISCIPLINES = "$STUDENT_RECORDS_API/v1/disciplines"
        }

        object Financial {
            private const val FINANCIAL_API = "$SECRETARY_API/financial"
            const val FEATURES = "$FINANCIAL_API/v1/features"
            const val EXTRACT = "$FINANCIAL_API/v1/extract"
            const val DEBTS = "$FINANCIAL_API/v1/debts"
        }

    }
}