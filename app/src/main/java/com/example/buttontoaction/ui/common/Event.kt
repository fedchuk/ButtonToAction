package com.example.buttontoaction.ui.common

sealed class Event {
    data class ShowSnackBarError(val code: Int, val text: String) : Event()
}
