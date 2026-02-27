package com.failsafe.domain.example

interface ExampleRepository {
    fun find(id: Long): ExampleModel?
}
