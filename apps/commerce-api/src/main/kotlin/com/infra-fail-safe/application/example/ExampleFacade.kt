package com.infra-fail-safe.application.example

import com.infra-fail-safe.domain.example.ExampleService
import org.springframework.stereotype.Component

@Component
class ExampleFacade(
    private val exampleService: ExampleService,
) {
    fun getExample(id: Long): ExampleInfo {
        return exampleService.getExample(id)
            .let { ExampleInfo.from(it) }
    }
}
