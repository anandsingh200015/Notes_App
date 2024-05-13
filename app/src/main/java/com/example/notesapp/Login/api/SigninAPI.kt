package com.example.notesapp.Login.api

import com.example.notesapp.Login.model.SigninRequest
import com.example.notesapp.Login.model.SigninResponse
import dagger.Provides
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

interface SigninAPI{


    @POST("users/signin")
    suspend fun sendSignInRequest(@Body SigninRequest: SigninRequest) : Response<SigninResponse>

}