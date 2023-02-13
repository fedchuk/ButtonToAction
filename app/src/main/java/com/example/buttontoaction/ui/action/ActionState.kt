package com.example.buttontoaction.ui.action

sealed class ActionState {
    object AnimationAction: ActionState()
    object ToastMessageAction: ActionState()
    object CallAction: ActionState()
    object NotificationAction: ActionState()
}
