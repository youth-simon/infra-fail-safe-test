package com.infra-fail-safe.domain.example

interface ExampleRepository {
    fun find(id: Long): ExampleModel?
}
