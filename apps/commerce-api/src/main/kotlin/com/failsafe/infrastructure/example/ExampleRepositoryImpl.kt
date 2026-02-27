package com.failsafe.infrastructure.example

import com.failsafe.domain.example.ExampleModel
import com.failsafe.domain.example.ExampleRepository
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
