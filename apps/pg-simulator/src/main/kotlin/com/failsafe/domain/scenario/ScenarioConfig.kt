package com.failsafe.domain.scenario

data class ScenarioConfig(
    val enabled: Boolean = true,
    val failureRate: Int = 0,
    val delayMin: Long = 0,
    val delayMax: Long = 0,
    val partialDegradation: Map<String, Int> = emptyMap(),
)
