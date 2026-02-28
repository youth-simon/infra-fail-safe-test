package com.failsafe.application.point

import com.failsafe.domain.point.PointAccumulation
import com.failsafe.domain.point.PointStatus
import java.time.LocalDateTime

data class PointInfo(
    val pointTransactionId: String,
    val earnedPoints: Long,
    val status: PointStatus,
    val accumulatedAt: LocalDateTime,
) {
    companion object {
        fun from(accumulation: PointAccumulation): PointInfo =
            PointInfo(
                pointTransactionId = accumulation.pointTransactionId,
                earnedPoints = accumulation.earnedPoints,
                status = accumulation.status,
                accumulatedAt = accumulation.accumulatedAt,
            )
    }
}
