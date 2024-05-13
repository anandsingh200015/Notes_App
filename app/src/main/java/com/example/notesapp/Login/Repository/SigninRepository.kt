package com.example.notesapp.Login.Repository

import android.content.Context
import android.util.Log
import com.example.notesapp.Login.api.SigninAPI
import com.example.notesapp.Login.model.SigninRequest
import com.example.notesapp.Login.model.SigninResult
import com.example.notesapp.Token.TokenManager
import javax.inject.Inject

interface ISigninRepository {
    suspend fun SigninRequest(signinRequest: SigninRequest) : SigninResult
}
class SigninRepository @Inject constructor(val service : SigninAPI, val tokenManager : TokenManager) : ISigninRepository {


    override suspend fun SigninRequest(signinRequest: SigninRequest): SigninResult {


        return try {
            val response = service.sendSignInRequest(signinRequest)
            Log.d("SignInRepo", response.body().toString())
            if (response.isSuccessful) {
                tokenManager.saveToken(response.body()!!.token)
                 SigninResult.Success
            } else {
                val errorBody = response.errorBody()?.string() ?: "Error logging in"
                val errorCode = response.code()
                if (errorCode == 400) {
                    SigninResult.Error("User Does not Exists")
                } else {
                    Log.d("SignupRepo", "Error logged $errorBody")
                    SigninResult.Error("Something Went Wrong")
                }
            }
        } catch (e: Exception) {
            SigninResult.Error(e.message.toString())
        }


    }
}

