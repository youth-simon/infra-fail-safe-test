package com.failsafe.interfaces.api.notification

import com.failsafe.application.notification.SmsNotificationCommand
import com.failsafe.application.notification.SmsNotificationInfo
import com.failsafe.support.error.CoreException
import com.failsafe.support.error.ErrorType
import java.time.LocalDateTime

object SmsNotificationDto {
    data class SmsRequest(
        val userId: String,
        val phoneNumber: String,
        val message: String,
        val referenceId: String,
    ) {
        fun validate() {
            if (userId.isBlank()) {
                throw CoreException(ErrorType.BAD_REQUEST, "사용자 ID는 필수입니다.")
            }
            if (phoneNumber.isBlank()) {
                throw CoreException(ErrorType.BAD_REQUEST, "전화번호는 필수입니다.")
            }
            if (message.isBlank()) {
                throw CoreException(ErrorType.BAD_REQUEST, "메시지는 필수입니다.")
            }
            if (referenceId.isBlank()) {
                throw CoreException(ErrorType.BAD_REQUEST, "참조 ID는 필수입니다.")
            }
        }

        fun toCommand(userId: String): SmsNotificationCommand.Send =
            SmsNotificationCommand.Send(
                userId = userId,
                phoneNumber = phoneNumber,
                message = message,
                referenceId = referenceId,
            )
    }

    data class SmsResponse(
        val notificationId: String,
        val status: String,
        val sentAt: LocalDateTime,
    ) {
        companion object {
            fun from(info: SmsNotificationInfo): SmsResponse =
                SmsResponse(
                    notificationId = info.notificationId,
                    status = info.status.name,
                    sentAt = info.sentAt,
                )
        }
    }
}
