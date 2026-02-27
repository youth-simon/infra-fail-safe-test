package com.failsafe.application.example

import com.failsafe.domain.example.ExampleModel

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
