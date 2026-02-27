package com.failsafe.domain.payment

import com.failsafe.application.payment.TransactionInfo

interface PaymentRelay {
    fun notify(callbackUrl: String, transactionInfo: TransactionInfo)
}
