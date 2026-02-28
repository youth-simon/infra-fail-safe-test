package com.failsafe.infrastructure.point

import com.failsafe.domain.point.PointAccumulation
import org.springframework.data.jpa.repository.JpaRepository

interface PointJpaRepository : JpaRepository<PointAccumulation, String> {
    fun findByUserIdAndOrderId(
        userId: String,
        orderId: String,
    ): PointAccumulation?
}
