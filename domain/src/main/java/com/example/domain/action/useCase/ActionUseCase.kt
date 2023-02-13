package com.example.domain.action.useCase

import com.example.domain.action.ActionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActionUseCase(private val actionRepository: ActionRepository) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        actionRepository.getActions()
    }
}