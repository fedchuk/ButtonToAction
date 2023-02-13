package com.example.domain.model

enum class ActionType(val value: String) {
    ANIMATION("animation"),
    TOAST("toast"),
    CALL("call"),
    NOTIFICATION("notification");

    companion object {
        fun findAction(value: String): ActionType? = ActionType.values().find { it.value == value }
    }
}
