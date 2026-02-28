package com.failsafe.domain.notification

interface SmsNotificationRepository {
    fun save(notification: SmsNotification): SmsNotification
}
