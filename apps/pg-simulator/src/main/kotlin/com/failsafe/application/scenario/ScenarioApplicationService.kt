package com.failsafe.application.scenario

import com.failsafe.domain.scenario.ScenarioConfig
import com.failsafe.domain.scenario.ScenarioDomain
import com.failsafe.support.error.CoreException
import com.failsafe.support.error.ErrorType
import org.springframework.stereotype.Component

@Component
class ScenarioApplicationService(
    private val scenarioConfigHolder: ScenarioConfigHolder,
) {
    fun getConfig(domain: ScenarioDomain): ScenarioConfig = scenarioConfigHolder.getConfig(domain.domainName)

    fun updateConfig(
        domain: ScenarioDomain,
        update: ScenarioConfigHolder.ScenarioConfigUpdate,
    ): ScenarioConfig {
        validate(domain, update)
        scenarioConfigHolder.updateConfig(domain.domainName, update)
        return scenarioConfigHolder.getConfig(domain.domainName)
    }

    private fun validate(
        domain: ScenarioDomain,
        update: ScenarioConfigHolder.ScenarioConfigUpdate,
    ) {
        update.failureRate?.let {
            if (it < 0 || it > 100) {
                throw CoreException(ErrorType.BAD_REQUEST, "failureRate must be between 0 and 100")
            }
        }

        update.delayMin?.let {
            if (it < 0 || it > 30000) {
                throw CoreException(ErrorType.BAD_REQUEST, "delayMin must be between 0 and 30000")
            }
        }

        update.delayMax?.let {
            if (it < 0 || it > 30000) {
                throw CoreException(ErrorType.BAD_REQUEST, "delayMax must be between 0 and 30000")
            }
        }

        val current = scenarioConfigHolder.getConfig(domain.domainName)
        val effectiveDelayMin = update.delayMin ?: current.delayMin
        val effectiveDelayMax = update.delayMax ?: current.delayMax

        if (effectiveDelayMin > effectiveDelayMax) {
            throw CoreException(ErrorType.BAD_REQUEST, "delayMin must be less than or equal to delayMax")
        }
    }
}
