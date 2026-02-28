package com.failsafe.domain.point

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "point_accumulations",
    uniqueConstraints = [UniqueConstraint(name = "uk_point_user_order", columnNames = ["user_id", "order_id"])],
)
class PointAccumulation(
    @Id
    @Column(name = "point_transaction_id", nullable = false, unique = true)
    val pointTransactionId: String,
    @Column(name = "user_id", nullable = false)
    val userId: String,
    @Column(name = "order_id", nullable = false)
    val orderId: String,
    @Column(name = "amount", nullable = false)
    val amount: Long,
    @Column(name = "point_rate", nullable = false)
    val pointRate: Double,
    @Column(name = "earned_points", nullable = false)
    val earnedPoints: Long,
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: PointStatus,
    @Column(name = "accumulated_at", nullable = false)
    val accumulatedAt: LocalDateTime,
) {
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        private set

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
        private set
}
