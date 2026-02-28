package com.failsafe.application.fraud

import com.failsafe.domain.fraud.FraudCheck
import com.failsafe.domain.fraud.FraudCheckRepository
import com.failsafe.domain.fraud.FraudCheckResult
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Component
class FraudCheckApplicationService(
    private val fraudCheckRepository: FraudCheckRepository,
) {
    @Transactional
    fun check(command: FraudCheckCommand.Check): FraudCheckInfo {
        command.validate()

        val checkId = "fc-${UUID.randomUUID()}"
        val rate = (1..100).random()

        val (result, score, reason) =
            when {
                rate <= 85 -> Triple(FraudCheckResult.PASS, (0..30).random(), "Normal transaction pattern")
                rate <= 95 -> Triple(FraudCheckResult.REJECT, (70..100).random(), "Suspicious transaction pattern detected")
                else -> Triple(FraudCheckResult.REVIEW, (40..69).random(), "Transaction requires manual review")
            }

        val fraudCheck =
            fraudCheckRepository.save(
                FraudCheck(
                    checkId = checkId,
                    userId = command.userId,
                    orderId = command.orderId,
                    amount = command.amount,
                    cardType = command.cardType,
                    cardNo = command.cardNo,
                    result = result,
                    score = score,
                    reason = reason,
                    checkedAt = LocalDateTime.now(),
                ),
            )

        return FraudCheckInfo.from(fraudCheck)
    }
}
