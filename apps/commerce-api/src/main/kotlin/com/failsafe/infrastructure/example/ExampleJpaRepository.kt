package com.failsafe.infrastructure.example

import com.failsafe.domain.example.ExampleModel
import org.springframework.data.jpa.repository.JpaRepository

interface ExampleJpaRepository : JpaRepository<ExampleModel, Long>
