package com.failsafe.infrastructure.fraud

import com.failsafe.domain.fraud.FraudCheck
import org.springframework.data.jpa.repository.JpaRepository

interface FraudCheckJpaRepository : JpaRepository<FraudCheck, String>
