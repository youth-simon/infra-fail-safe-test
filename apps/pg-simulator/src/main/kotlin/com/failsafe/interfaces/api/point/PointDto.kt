package com.failsafe.interfaces.api.point

import com.failsafe.application.point.PointCommand
import com.failsafe.application.point.PointInfo
import com.failsafe.support.error.CoreException
import com.failsafe.support.error.ErrorType
import java.time.LocalDateTime

object PointDto {
    data class AccumulateRequest(
        val userId: String,
        val orderId: String,
        val amount: Long,
        val pointRate: Double,
    ) {
        fun validate() {
            if (orderId.isBlank() || orderId.length < 6) {
                throw CoreException(ErrorType.BAD_REQUEST, "주문 ID는 6자리 이상 문자열이어야 합니다.")
            }
            if (amount <= 0) {
                throw CoreException(ErrorType.BAD_REQUEST, "결제금액은 양의 정수여야 합니다.")
            }
            if (pointRate <= 0.0 || pointRate > 1.0) {
                throw CoreException(ErrorType.BAD_REQUEST, "포인트 적립률은 0 초과 1.0 이하여야 합니다.")
            }
        }

        fun toCommand(userId: String): PointCommand.Accumulate =
            PointCommand.Accumulate(
                userId = userId,
                orderId = orderId,
                amount = amount,
                pointRate = pointRate,
            )
    }

    data class AccumulateResponse(
        val pointTransactionId: String,
        val earnedPoints: Long,
        val status: String,
        val accumulatedAt: LocalDateTime,
    ) {
        companion object {
            fun from(info: PointInfo): AccumulateResponse =
                AccumulateResponse(
                    pointTransactionId = info.pointTransactionId,
                    earnedPoints = info.earnedPoints,
                    status = info.status.name,
                    accumulatedAt = info.accumulatedAt,
                )
        }
    }
}
