package com.failsafe.application.notification

object SmsNotificationCommand {
    data class Send(
        val userId: String,
        val phoneNumber: String,
        val message: String,
        val referenceId: String,
    )
}
