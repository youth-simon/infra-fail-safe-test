package com.infra-fail-safe.domain.payment

interface PaymentEventPublisher {
    fun publish(event: PaymentEvent.PaymentCreated)
    fun publish(event: PaymentEvent.PaymentHandled)
}
