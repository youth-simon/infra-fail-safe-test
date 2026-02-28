package com.failsafe.application.notification

import com.failsafe.domain.notification.NotificationStatus
import com.failsafe.domain.notification.SmsNotification
import java.time.LocalDateTime

data class SmsNotificationInfo(
    val notificationId: String,
    val status: NotificationStatus,
    val sentAt: LocalDateTime,
) {
    companion object {
        fun from(notification: SmsNotification): SmsNotificationInfo =
            SmsNotificationInfo(
                notificationId = notification.notificationId,
                status = notification.status,
                sentAt = notification.sentAt,
            )
    }
}
