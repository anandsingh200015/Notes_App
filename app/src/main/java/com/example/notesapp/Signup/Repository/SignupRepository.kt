package com.example.notesapp.Signup.Repository

import android.content.Context
import android.util.Log
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.notesapp.Signup.api.SignupAPI
import com.example.notesapp.Signup.model.SignupRequest
import com.example.notesapp.Signup.model.SignupResult
import com.example.notesapp.Token.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

interface ISignupRepository{
    suspend fun signupRequest(signupRequest : SignupRequest) : SignupResult
}
class SignupRepository @Inject constructor( val service : SignupAPI, val tokenManager: TokenManager) : ISignupRepository {




    override suspend fun signupRequest(signupRequest: SignupRequest): SignupResult {
    return try {
            val response = service.sendSignupRequest(signupRequest)
            Log.d("NOTESAPP",response.body().toString())
            if (response.isSuccessful){
                tokenManager.saveToken(response.body()!!.token)
                 SignupResult.Success
            }
            else{
                val errorBody = response.errorBody()?.string() ?: "Error logging in"
                val errorCode = response.code()
                if (errorCode == 400){
                    SignupResult.Error("User Already Exists")
                }
                else{
                    Log.d("SignupRepo","Error logged $errorBody")
                    SignupResult.Error("Something Went Wrong")
                }

            }
        }
        catch (e : Exception){
             SignupResult.Error(e.localizedMessage.toString())
        }

    }

}