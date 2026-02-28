package com.failsafe.application.point

import com.failsafe.domain.point.PointAccumulation
import com.failsafe.domain.point.PointAccumulationRepository
import com.failsafe.domain.point.PointStatus
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Component
class PointApplicationService(
    private val pointAccumulationRepository: PointAccumulationRepository,
) {
    @Transactional
    fun accumulate(command: PointCommand.Accumulate): PointInfo {
        val existing = pointAccumulationRepository.findByUserIdAndOrderId(command.userId, command.orderId)
        if (existing != null) {
            return PointInfo.from(existing)
        }

        val pointTransactionId = "pt-${UUID.randomUUID()}"
        val earnedPoints = (command.amount * command.pointRate).toLong()

        val status =
            if ((1..100).random() <= 90) {
                PointStatus.COMPLETED
            } else {
                PointStatus.FAILED
            }

        return try {
            val accumulation =
                pointAccumulationRepository.save(
                    PointAccumulation(
                        pointTransactionId = pointTransactionId,
                        userId = command.userId,
                        orderId = command.orderId,
                        amount = command.amount,
                        pointRate = command.pointRate,
                        earnedPoints = earnedPoints,
                        status = status,
                        accumulatedAt = LocalDateTime.now(),
                    ),
                )
            PointInfo.from(accumulation)
        } catch (e: DataIntegrityViolationException) {
            val race =
                pointAccumulationRepository.findByUserIdAndOrderId(command.userId, command.orderId)
                    ?: throw e
            PointInfo.from(race)
        }
    }
}
