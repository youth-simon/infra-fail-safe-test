package com.failsafe.application.scenario

import com.failsafe.support.error.CoreException
import com.failsafe.support.error.ErrorType
import org.springframework.stereotype.Component

@Component
class SimulatorBehavior(
    private val configHolder: ScenarioConfigHolder,
) {
    fun applySimulation(
        domain: String,
        conditionKey: String? = null,
    ) {
        val config = configHolder.getConfig(domain)

        // 1. Check enabled (503 = total outage, distinct from 500 = intermittent failure)
        if (!config.enabled) {
            throw CoreException(ErrorType.SERVICE_UNAVAILABLE, "Service is currently unavailable")
        }

        // 2. Apply delay
        if (config.delayMin > 0 && config.delayMax > 0) {
            val delay = (config.delayMin..config.delayMax).random()
            Thread.sleep(delay)
        }

        // 3. Check failure rate (with partial degradation)
        var effectiveFailureRate = config.failureRate
        if (conditionKey != null) {
            effectiveFailureRate += config.partialDegradation[conditionKey] ?: 0
            effectiveFailureRate = effectiveFailureRate.coerceAtMost(100)
        }
        if (effectiveFailureRate > 0 && (1..100).random() <= effectiveFailureRate) {
            throw CoreException(ErrorType.INTERNAL_ERROR, "$domain service is temporarily unavailable")
        }
    }
}
