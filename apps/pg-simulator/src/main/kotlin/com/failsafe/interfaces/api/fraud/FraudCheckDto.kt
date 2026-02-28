package com.failsafe.interfaces.api.fraud

import com.failsafe.application.fraud.FraudCheckCommand
import com.failsafe.application.fraud.FraudCheckInfo
import com.failsafe.domain.fraud.FraudCheckCardType
import com.failsafe.support.error.CoreException
import com.failsafe.support.error.ErrorType
import java.time.LocalDateTime

object FraudCheckDto {
    data class FraudCheckRequest(
        val userId: String,
        val orderId: String,
        val amount: Long,
        val cardType: CardTypeDto,
        val cardNo: String,
    ) {
        companion object {
            private val REGEX_CARD_NO = Regex("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")
        }

        fun validate() {
            if (orderId.isBlank() || orderId.length < 6) {
                throw CoreException(ErrorType.BAD_REQUEST, "주문 ID는 6자리 이상 문자열이어야 합니다.")
            }
            if (!REGEX_CARD_NO.matches(cardNo)) {
                throw CoreException(ErrorType.BAD_REQUEST, "카드 번호는 xxxx-xxxx-xxxx-xxxx 형식이어야 합니다.")
            }
            if (amount <= 0) {
                throw CoreException(ErrorType.BAD_REQUEST, "결제금액은 양의 정수여야 합니다.")
            }
        }

        fun toCommand(userId: String): FraudCheckCommand.Check =
            FraudCheckCommand.Check(
                userId = userId,
                orderId = orderId,
                amount = amount,
                cardType = cardType.toFraudCheckCardType(),
                cardNo = cardNo,
            )
    }

    data class FraudCheckResponse(
        val checkId: String,
        val result: String,
        val score: Int,
        val reason: String?,
        val checkedAt: LocalDateTime,
    ) {
        companion object {
            fun from(info: FraudCheckInfo): FraudCheckResponse =
                FraudCheckResponse(
                    checkId = info.checkId,
                    result = info.result.name,
                    score = info.score,
                    reason = info.reason,
                    checkedAt = info.checkedAt,
                )
        }
    }

    enum class CardTypeDto {
        SAMSUNG,
        KB,
        HYUNDAI,
        ;

        fun toFraudCheckCardType(): FraudCheckCardType =
            when (this) {
                SAMSUNG -> FraudCheckCardType.SAMSUNG
                KB -> FraudCheckCardType.KB
                HYUNDAI -> FraudCheckCardType.HYUNDAI
            }

        companion object {
            fun from(cardType: FraudCheckCardType) =
                when (cardType) {
                    FraudCheckCardType.SAMSUNG -> SAMSUNG
                    FraudCheckCardType.KB -> KB
                    FraudCheckCardType.HYUNDAI -> HYUNDAI
                }
        }
    }
}
