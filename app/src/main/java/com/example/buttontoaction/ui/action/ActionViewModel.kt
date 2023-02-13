package com.example.buttontoaction.ui.action

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.buttontoaction.ui.common.CommonViewModelImpl
import com.example.domain.action.useCase.ActionUseCase
import com.example.domain.common.ExecutionResult
import com.example.domain.model.Action
import com.example.domain.model.ActionType
import kotlinx.coroutines.launch
import java.util.Calendar

class ActionViewModel(
    private val actionUseCase: ActionUseCase
): CommonViewModelImpl() {
    val state = MutableLiveData<ActionState?>()

    fun getAction() = viewModelScope.launch {
        when (val result = actionUseCase()) {
            is ExecutionResult.Error -> {
                showSnackBarError(code = result.code ?: 0, message = result.error ?: "")
            }
            is ExecutionResult.Success -> {
                state.postValue(getCurrentAction(actions = result.data))
            }
        }
    }

    private fun getCurrentAction(actions: List<Action>): ActionState? {
        var priority = 0
        var result: ActionState? = null
        actions.forEach ac@ { action ->
            if (action.enabled && action.validDays.contains(getCurrentNumberOfDays())) {
                if (action.priority > priority) {
                    priority = action.priority
                    action.type?.let {
                        result = getActionState(it)
                    }
                }
            }
        }
        return result
    }

    private fun getCurrentNumberOfDays() = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

    private fun getActionState(actionType: ActionType) = when (actionType) {
        ActionType.ANIMATION -> ActionState.AnimationAction
        ActionType.TOAST -> ActionState.ToastMessageAction
        ActionType.CALL -> ActionState.CallAction
        ActionType.NOTIFICATION -> ActionState.NotificationAction
    }
}