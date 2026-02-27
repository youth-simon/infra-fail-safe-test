package com.failsafe.infrastructure.payment

import com.failsafe.domain.payment.PaymentEvent
import com.failsafe.domain.payment.PaymentEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class PaymentCoreEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher,
) : PaymentEventPublisher {
    override fun publish(event: PaymentEvent.PaymentCreated) {
        applicationEventPublisher.publishEvent(event)
    }

    override fun publish(event: PaymentEvent.PaymentHandled) {
        applicationEventPublisher.publishEvent(event)
    }
}
