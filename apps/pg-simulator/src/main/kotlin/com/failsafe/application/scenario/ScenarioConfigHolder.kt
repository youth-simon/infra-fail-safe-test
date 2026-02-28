package com.failsafe.application.scenario

import com.failsafe.domain.scenario.ScenarioConfig
import com.failsafe.domain.scenario.ScenarioDomain
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class ScenarioConfigHolder {
    private val configs = ConcurrentHashMap<String, ScenarioConfig>()

    init {
        configs[ScenarioDomain.PAYMENT.domainName] =
            ScenarioConfig(
                enabled = true,
                failureRate = 40,
                delayMin = 100,
                delayMax = 1000,
            )
        configs[ScenarioDomain.FRAUD_CHECK.domainName] =
            ScenarioConfig(
                enabled = true,
                failureRate = 15,
                delayMin = 100,
                delayMax = 500,
            )
        configs[ScenarioDomain.SMS.domainName] =
            ScenarioConfig(
                enabled = true,
                failureRate = 20,
                delayMin = 300,
                delayMax = 1200,
            )
        configs[ScenarioDomain.POINT.domainName] =
            ScenarioConfig(
                enabled = true,
                failureRate = 10,
                delayMin = 50,
                delayMax = 300,
            )
    }

    fun getConfig(domain: String): ScenarioConfig = configs[domain] ?: ScenarioConfig()

    fun updateConfig(
        domain: String,
        update: ScenarioConfigUpdate,
    ) {
        val current = getConfig(domain)
        configs[domain] =
            current.copy(
                enabled = update.enabled ?: current.enabled,
                failureRate = update.failureRate ?: current.failureRate,
                delayMin = update.delayMin ?: current.delayMin,
                delayMax = update.delayMax ?: current.delayMax,
                partialDegradation = update.partialDegradation ?: current.partialDegradation,
            )
    }

    data class ScenarioConfigUpdate(
        val enabled: Boolean? = null,
        val failureRate: Int? = null,
        val delayMin: Long? = null,
        val delayMax: Long? = null,
        val partialDegradation: Map<String, Int>? = null,
    )
}
