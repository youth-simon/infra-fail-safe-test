package com.failsafe.infrastructure.notification

import com.failsafe.domain.notification.SmsNotification
import com.failsafe.domain.notification.SmsNotificationRepository
import org.springframework.stereotype.Component

@Component
class SmsNotificationCoreRepository(
    private val smsNotificationJpaRepository: SmsNotificationJpaRepository,
) : SmsNotificationRepository {
    override fun save(notification: SmsNotification): SmsNotification {
        return smsNotificationJpaRepository.save(notification)
    }
}
