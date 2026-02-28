package com.failsafe.infrastructure.notification

import com.failsafe.domain.notification.SmsNotification
import org.springframework.data.jpa.repository.JpaRepository

interface SmsNotificationJpaRepository : JpaRepository<SmsNotification, String>
