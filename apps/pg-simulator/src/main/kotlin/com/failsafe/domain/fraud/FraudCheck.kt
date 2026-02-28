package com.failsafe.domain.fraud

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(
    name = "fraud_checks",
    indexes = [Index(name = "idx_fraud_user_order", columnList = "user_id, order_id")],
)
class FraudCheck(
    @Id
    @Column(name = "check_id", nullable = false, unique = true)
    val checkId: String,
    @Column(name = "user_id", nullable = false)
    val userId: String,
    @Column(name = "order_id", nullable = false)
    val orderId: String,
    @Column(name = "amount", nullable = false)
    val amount: Long,
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    val cardType: FraudCheckCardType,
    @Column(name = "card_no", nullable = false)
    val cardNo: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "result", nullable = false)
    val result: FraudCheckResult,
    @Column(name = "score", nullable = false)
    val score: Int,
    @Column(name = "reason", nullable = true)
    val reason: String?,
    @Column(name = "checked_at", nullable = false)
    val checkedAt: LocalDateTime,
) {
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        private set

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
        private set
}
