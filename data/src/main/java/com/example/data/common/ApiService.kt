package com.example.data.common

import com.example.data.action.models.ActionEntity
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/androidexam/butto_to_action_config.json")
    fun buttonToAction(): Call<List<ActionEntity>>
}