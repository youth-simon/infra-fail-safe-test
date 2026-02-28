package com.failsafe.interfaces.api.scenario

import com.failsafe.application.scenario.ScenarioConfigHolder
import com.failsafe.domain.scenario.ScenarioConfig
import com.failsafe.domain.scenario.ScenarioDomain

object ScenarioDto {
    data class ConfigUpdateRequest(
        val enabled: Boolean? = null,
        val failureRate: Int? = null,
        val delayMin: Long? = null,
        val delayMax: Long? = null,
        val partialDegradation: Map<String, Int>? = null,
    ) {
        fun toUpdate(): ScenarioConfigHolder.ScenarioConfigUpdate =
            ScenarioConfigHolder.ScenarioConfigUpdate(
                enabled = enabled,
                failureRate = failureRate,
                delayMin = delayMin,
                delayMax = delayMax,
                partialDegradation = partialDegradation,
            )
    }

    data class ConfigResponse(
        val domain: String,
        val enabled: Boolean,
        val failureRate: Int,
        val delayMin: Long,
        val delayMax: Long,
        val partialDegradation: Map<String, Int>,
    ) {
        companion object {
            fun from(
                domain: ScenarioDomain,
                config: ScenarioConfig,
            ): ConfigResponse =
                ConfigResponse(
                    domain = domain.domainName,
                    enabled = config.enabled,
                    failureRate = config.failureRate,
                    delayMin = config.delayMin,
                    delayMax = config.delayMax,
                    partialDegradation = config.partialDegradation,
                )
        }
    }
}
