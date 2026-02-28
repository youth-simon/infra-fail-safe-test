package com.failsafe.application.notification

import com.failsafe.domain.notification.NotificationStatus
import com.failsafe.domain.notification.SmsNotification
import com.failsafe.domain.notification.SmsNotificationRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Component
class SmsNotificationApplicationService(
    private val smsNotificationRepository: SmsNotificationRepository,
) {
    @Transactional
    fun send(command: SmsNotificationCommand.Send): SmsNotificationInfo {
        val notificationId = "sms-${UUID.randomUUID()}"

        val status =
            if ((1..100).random() <= 90) {
                NotificationStatus.SENT
            } else {
                NotificationStatus.FAILED
            }

        val notification =
            smsNotificationRepository.save(
                SmsNotification(
                    notificationId = notificationId,
                    userId = command.userId,
                    phoneNumber = command.phoneNumber,
                    message = command.message,
                    referenceId = command.referenceId,
                    status = status,
                    sentAt = LocalDateTime.now(),
                ),
            )

        return SmsNotificationInfo.from(notification)
    }
}
