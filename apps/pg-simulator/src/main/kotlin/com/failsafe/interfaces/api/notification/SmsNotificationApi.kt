package com.failsafe.interfaces.api.notification

import com.failsafe.application.notification.SmsNotificationApplicationService
import com.failsafe.application.scenario.SimulatorBehavior
import com.failsafe.domain.user.UserInfo
import com.failsafe.interfaces.api.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/notifications")
class SmsNotificationApi(
    private val smsNotificationApplicationService: SmsNotificationApplicationService,
    private val simulatorBehavior: SimulatorBehavior,
) {
    @PostMapping("/sms")
    fun send(
        userInfo: UserInfo,
        @RequestBody request: SmsNotificationDto.SmsRequest,
    ): ApiResponse<SmsNotificationDto.SmsResponse> {
        request.validate()

        simulatorBehavior.applySimulation("sms")

        return smsNotificationApplicationService.send(request.toCommand(userInfo.userId))
            .let { SmsNotificationDto.SmsResponse.from(it) }
            .let { ApiResponse.success(it) }
    }
}
