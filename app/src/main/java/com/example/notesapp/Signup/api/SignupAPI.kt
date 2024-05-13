package com.example.notesapp.Signup.api

import com.example.notesapp.Login.model.SigninRequest
import com.example.notesapp.Login.model.SigninResponse
import com.example.notesapp.Signup.model.Signup
import com.example.notesapp.Signup.model.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupAPI{

    @POST("users/signup")
suspend fun sendSignupRequest(@Body data : SignupRequest) : Response<Signup>



}