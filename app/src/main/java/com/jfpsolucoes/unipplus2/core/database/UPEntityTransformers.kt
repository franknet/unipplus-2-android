package com.jfpsolucoes.unipplus2.core.database

import com.jfpsolucoes.unipplus2.core.database.entities.UPCredentialsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity

class UPEntityTransformers {
    companion object {
        fun credentials(result: List<UPCredentialsEntity>): UPCredentialsEntity {
            return result.lastOrNull() ?: UPCredentialsEntity()
        }

        fun settings(result: List<UPSettingsEntity>): UPSettingsEntity {
            return result.lastOrNull() ?: UPSettingsEntity()
        }
    }
}