package com.infra-fail-safe.infrastructure.payment

import com.infra-fail-safe.domain.payment.PaymentEvent
import com.infra-fail-safe.domain.payment.PaymentEventPublisher
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
