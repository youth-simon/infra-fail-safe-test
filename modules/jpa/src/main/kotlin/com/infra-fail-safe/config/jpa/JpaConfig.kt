package com.infra-fail-safe.config.jpa

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = ["com.infra-fail-safe"])
@EnableJpaRepositories(basePackages = ["com.infra-fail-safe.infrastructure"])
class JpaConfig
