package com.example.buttontoaction.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class CommonViewModelImpl : ViewModel(), CommonViewModel {
    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    override val eventsFlow = eventChannel.receiveAsFlow()

    protected fun showSnackBarError(code: Int, message: String) {
        viewModelScope.launch {
            eventChannel.send(Event.ShowSnackBarError(code, message))
        }
    }
}