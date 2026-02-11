package com.infra-fail-safe.infrastructure.example

import com.infra-fail-safe.domain.example.ExampleModel
import com.infra-fail-safe.domain.example.ExampleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ExampleRepositoryImpl(
    private val exampleJpaRepository: ExampleJpaRepository,
) : ExampleRepository {
    override fun find(id: Long): ExampleModel? {
        return exampleJpaRepository.findByIdOrNull(id)
    }
}
