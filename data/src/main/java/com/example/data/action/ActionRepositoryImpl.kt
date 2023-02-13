package com.example.data.action

import com.example.data.common.ApiService
import com.example.data.common.apiCall
import com.example.domain.action.ActionRepository
import com.example.domain.common.ExecutionResult
import com.example.domain.model.Action

class ActionRepositoryImpl(private val apiService: ApiService): ActionRepository {

    override fun getActions(): ExecutionResult<List<Action>> {
        return apiCall {
            apiService.buttonToAction()
        }.toResource { data ->
            data.map { it.toModel() }
        }
    }
}