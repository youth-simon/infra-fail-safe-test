package com.failsafe.application.point

object PointCommand {
    data class Accumulate(
        val userId: String,
        val orderId: String,
        val amount: Long,
        val pointRate: Double,
    )
}
