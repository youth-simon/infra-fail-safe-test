package com.infra-fail-safe.infrastructure.example

import com.infra-fail-safe.domain.example.ExampleModel
import org.springframework.data.jpa.repository.JpaRepository

interface ExampleJpaRepository : JpaRepository<ExampleModel, Long>
