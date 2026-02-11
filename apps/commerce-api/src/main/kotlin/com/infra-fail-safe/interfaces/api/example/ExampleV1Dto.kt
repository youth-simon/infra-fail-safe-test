package com.infra-fail-safe.interfaces.api.example

import com.infra-fail-safe.application.example.ExampleInfo

class ExampleV1Dto {
    data class ExampleResponse(
        val id: Long,
        val name: String,
        val description: String,
    ) {
        companion object {
            fun from(info: ExampleInfo): ExampleResponse {
                return ExampleResponse(
                    id = info.id,
                    name = info.name,
                    description = info.description,
                )
            }
        }
    }
}
