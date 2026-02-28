package com.failsafe.application.fraud

import com.failsafe.domain.fraud.FraudCheck
import com.failsafe.domain.fraud.FraudCheckResult
import java.time.LocalDateTime

data class FraudCheckInfo(
    val checkId: String,
    val result: FraudCheckResult,
    val score: Int,
    val reason: String?,
    val checkedAt: LocalDateTime,
) {
    companion object {
        fun from(fraudCheck: FraudCheck): FraudCheckInfo =
            FraudCheckInfo(
                checkId = fraudCheck.checkId,
                result = fraudCheck.result,
                score = fraudCheck.score,
                reason = fraudCheck.reason,
                checkedAt = fraudCheck.checkedAt,
            )
    }
}
