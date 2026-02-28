package com.failsafe.interfaces.api.scenario

import com.failsafe.application.scenario.ScenarioApplicationService
import com.failsafe.domain.scenario.ScenarioDomain
import com.failsafe.interfaces.api.ApiResponse
import com.failsafe.support.error.CoreException
import com.failsafe.support.error.ErrorType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/internal/scenarios")
class ScenarioApi(
    private val scenarioApplicationService: ScenarioApplicationService,
) {
    @GetMapping("/{domain}/config")
    fun getConfig(
        @PathVariable("domain") domain: String,
    ): ApiResponse<ScenarioDto.ConfigResponse> {
        val scenarioDomain = parseDomain(domain)
        val config = scenarioApplicationService.getConfig(scenarioDomain)
        return ApiResponse.success(ScenarioDto.ConfigResponse.from(scenarioDomain, config))
    }

    @PostMapping("/{domain}/config")
    fun updateConfig(
        @PathVariable("domain") domain: String,
        @RequestBody request: ScenarioDto.ConfigUpdateRequest,
    ): ApiResponse<ScenarioDto.ConfigResponse> {
        val scenarioDomain = parseDomain(domain)
        val config = scenarioApplicationService.updateConfig(scenarioDomain, request.toUpdate())
        return ApiResponse.success(ScenarioDto.ConfigResponse.from(scenarioDomain, config))
    }

    private fun parseDomain(domain: String): ScenarioDomain =
        try {
            ScenarioDomain.fromDomainName(domain)
        } catch (e: IllegalArgumentException) {
            throw CoreException(ErrorType.BAD_REQUEST, e.message)
        }
}
