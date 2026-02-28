package com.failsafe.infrastructure.fraud

import com.failsafe.domain.fraud.FraudCheck
import com.failsafe.domain.fraud.FraudCheckRepository
import org.springframework.stereotype.Component

@Component
class FraudCheckCoreRepository(
    private val fraudCheckJpaRepository: FraudCheckJpaRepository,
) : FraudCheckRepository {
    override fun save(fraudCheck: FraudCheck): FraudCheck {
        return fraudCheckJpaRepository.save(fraudCheck)
    }
}
