package com.failsafe.domain.scenario

enum class ScenarioDomain(val domainName: String) {
    PAYMENT("payment"),
    FRAUD_CHECK("fraud-check"),
    SMS("sms"),
    POINT("point"),
    ;

    companion object {
        fun fromDomainName(name: String): ScenarioDomain =
            entries.find { it.domainName == name }
                ?: throw IllegalArgumentException("Unknown domain: $name")
    }
}
