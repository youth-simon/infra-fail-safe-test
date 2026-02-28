package com.failsafe.interfaces.api.payment

import com.failsafe.application.payment.PaymentApplicationService
import com.failsafe.application.scenario.SimulatorBehavior
import com.failsafe.domain.user.UserInfo
import com.failsafe.interfaces.api.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/payments")
class PaymentApi(
    private val paymentApplicationService: PaymentApplicationService,
    private val simulatorBehavior: SimulatorBehavior,
) {
    @PostMapping
    fun request(
        userInfo: UserInfo,
        @RequestBody request: PaymentDto.PaymentRequest,
    ): ApiResponse<PaymentDto.TransactionResponse> {
        request.validate()

        simulatorBehavior.applySimulation("payment")

        return paymentApplicationService.createTransaction(request.toCommand(userInfo.userId))
            .let { PaymentDto.TransactionResponse.from(it) }
            .let { ApiResponse.success(it) }
    }

    @GetMapping("/{transactionKey}")
    fun getTransaction(
        userInfo: UserInfo,
        @PathVariable("transactionKey") transactionKey: String,
    ): ApiResponse<PaymentDto.TransactionDetailResponse> {
        return paymentApplicationService.getTransactionDetailInfo(userInfo, transactionKey)
            .let { PaymentDto.TransactionDetailResponse.from(it) }
            .let { ApiResponse.success(it) }
    }

    @GetMapping
    fun getTransactionsByOrder(
        userInfo: UserInfo,
        @RequestParam("orderId", required = false) orderId: String,
    ): ApiResponse<PaymentDto.OrderResponse> {
        return paymentApplicationService.findTransactionsByOrderId(userInfo, orderId)
            .let { PaymentDto.OrderResponse.from(it) }
            .let { ApiResponse.success(it) }
    }
}
