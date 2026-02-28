package com.failsafe.interfaces.api.point

import com.failsafe.application.point.PointApplicationService
import com.failsafe.application.scenario.SimulatorBehavior
import com.failsafe.domain.user.UserInfo
import com.failsafe.interfaces.api.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/points")
class PointApi(
    private val pointApplicationService: PointApplicationService,
    private val simulatorBehavior: SimulatorBehavior,
) {
    @PostMapping("/accumulate")
    fun accumulate(
        userInfo: UserInfo,
        @RequestBody request: PointDto.AccumulateRequest,
    ): ApiResponse<PointDto.AccumulateResponse> {
        request.validate()

        simulatorBehavior.applySimulation("point")

        return pointApplicationService.accumulate(request.toCommand(userInfo.userId))
            .let { PointDto.AccumulateResponse.from(it) }
            .let { ApiResponse.success(it) }
    }
}
