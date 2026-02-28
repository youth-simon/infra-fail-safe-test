package com.failsafe.application.fraud

import com.failsafe.domain.fraud.FraudCheckCardType
import com.failsafe.support.error.CoreException
import com.failsafe.support.error.ErrorType

object FraudCheckCommand {
    data class Check(
        val userId: String,
        val orderId: String,
        val amount: Long,
        val cardType: FraudCheckCardType,
        val cardNo: String,
    ) {
        fun validate() {
            if (amount <= 0L) {
                throw CoreException(ErrorType.BAD_REQUEST, "요청 금액은 0 보다 큰 정수여야 합니다.")
            }
        }
    }
}
