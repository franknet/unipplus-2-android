package com.jfpsolucoes.unipplus2.core.networking.api

class UPApiEndpoints {
    companion object {
        const val BASE_URL = "https://southamerica-east1-unip-plus-2-a3fa1.cloudfunctions.net"
    }

    class Auth {
        companion object {
            const val SIGN_IN = "/auth/v1/sign-in"
            const val SYSTEMS = "/auth/v1/systems"
        }
    }

    class Secretary {
        companion object {
            const val FEATURES = "/secretary/v1/features"
        }

        class StudentRecords {
            companion object {
                const val DISCIPLINES = "/secretary/student-records/v1/disciplines"
            }
        }

        class Finance {
            companion object {

            }
        }

    }
}