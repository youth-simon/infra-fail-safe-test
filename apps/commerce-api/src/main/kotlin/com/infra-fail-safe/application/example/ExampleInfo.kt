package com.infra-fail-safe.application.example

import com.infra-fail-safe.domain.example.ExampleModel

data class ExampleInfo(
    val id: Long,
    val name: String,
    val description: String,
) {
    companion object {
        fun from(model: ExampleModel): ExampleInfo {
            return ExampleInfo(
                id = model.id,
                name = model.name,
                description = model.description,
            )
        }
    }
}
