package com.failsafe.domain.notification

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "sms_notifications",
    indexes = [Index(name = "idx_sms_user_ref", columnList = "user_id, reference_id")],
)
class SmsNotification(
    @Id
    @Column(name = "notification_id", nullable = false, unique = true)
    val notificationId: String,
    @Column(name = "user_id", nullable = false)
    val userId: String,
    @Column(name = "phone_number", nullable = false)
    val phoneNumber: String,
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    val message: String,
    @Column(name = "reference_id", nullable = false)
    val referenceId: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: NotificationStatus,
    @Column(name = "sent_at", nullable = false)
    val sentAt: LocalDateTime,
) {
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        private set

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
        private set
}
