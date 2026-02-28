package com.failsafe.infrastructure.point

import com.failsafe.domain.point.PointAccumulation
import com.failsafe.domain.point.PointAccumulationRepository
import org.springframework.stereotype.Component

@Component
class PointCoreRepository(
    private val pointJpaRepository: PointJpaRepository,
) : PointAccumulationRepository {
    override fun save(accumulation: PointAccumulation): PointAccumulation {
        return pointJpaRepository.save(accumulation)
    }

    override fun findByUserIdAndOrderId(
        userId: String,
        orderId: String,
    ): PointAccumulation? {
        return pointJpaRepository.findByUserIdAndOrderId(userId, orderId)
    }
}
