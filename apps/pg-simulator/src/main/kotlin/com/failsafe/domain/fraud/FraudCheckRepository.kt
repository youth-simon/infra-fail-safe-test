package com.failsafe.domain.fraud

interface FraudCheckRepository {
    fun save(fraudCheck: FraudCheck): FraudCheck
}
