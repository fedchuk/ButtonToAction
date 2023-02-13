package com.example.buttontoaction.ui.common

import androidx.lifecycle.LifecycleObserver
import kotlinx.coroutines.flow.Flow

interface CommonViewModel : LifecycleObserver {
    val eventsFlow: Flow<Event>
}