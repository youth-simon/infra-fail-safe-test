package com.failsafe.interfaces.api.fraud

import com.failsafe.application.fraud.FraudCheckApplicationService
import com.failsafe.application.scenario.SimulatorBehavior
import com.failsafe.domain.user.UserInfo
import com.failsafe.interfaces.api.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/fraud-check")
class FraudCheckApi(
    private val fraudCheckApplicationService: FraudCheckApplicationService,
    private val simulatorBehavior: SimulatorBehavior,
) {
    @PostMapping
    fun check(
        userInfo: UserInfo,
        @RequestBody request: FraudCheckDto.FraudCheckRequest,
    ): ApiResponse<FraudCheckDto.FraudCheckResponse> {
        request.validate()

        // Apply simulation (delay + failure rate + partial degradation by cardType)
        simulatorBehavior.applySimulation("fraud-check", conditionKey = request.cardType.name)

        // High-value transaction timeout simulation
        if (request.amount >= 500_000 && (1..100).random() <= 30) {
            Thread.sleep(3000)
        }

        return fraudCheckApplicationService.check(request.toCommand(userInfo.userId))
            .let { FraudCheckDto.FraudCheckResponse.from(it) }
            .let { ApiResponse.success(it) }
    }
}
