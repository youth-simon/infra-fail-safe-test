package com.infra-fail-safe.domain.payment

import com.infra-fail-safe.application.payment.TransactionInfo

interface PaymentRelay {
    fun notify(callbackUrl: String, transactionInfo: TransactionInfo)
}
