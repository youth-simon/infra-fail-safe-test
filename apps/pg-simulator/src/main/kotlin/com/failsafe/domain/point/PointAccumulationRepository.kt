package com.failsafe.domain.point

interface PointAccumulationRepository {
    fun save(accumulation: PointAccumulation): PointAccumulation

    fun findByUserIdAndOrderId(
        userId: String,
        orderId: String,
    ): PointAccumulation?
}
