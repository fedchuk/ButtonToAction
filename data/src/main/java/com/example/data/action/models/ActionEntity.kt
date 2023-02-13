package com.example.data.action.models

import com.example.domain.model.Action
import com.example.domain.model.ActionType

data class ActionEntity(
    val type: String,
    val enabled: Boolean,
    val priority: Int,
    val valid_days: List<Int>,
    val cool_down: Long
) {
    fun toModel() = Action(
        type = ActionType.findAction(type),
        enabled = enabled,
        priority = priority,
        validDays = valid_days,
        coolDown = cool_down
    )
}
