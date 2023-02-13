package com.example.domain.action

import com.example.domain.common.ExecutionResult
import com.example.domain.model.Action

interface ActionRepository {
    fun getActions(): ExecutionResult<List<Action>>
}