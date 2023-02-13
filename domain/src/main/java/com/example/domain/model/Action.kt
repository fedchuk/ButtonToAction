package com.example.domain.model

data class Action(
    val type: ActionType?,
    val enabled: Boolean,
    val priority: Int,
    val validDays: List<Int>,
    val coolDown: Long
)
