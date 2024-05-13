package com.example.notesapp.Notes.api

import android.util.Log
import com.example.notesapp.Token.TokenManager
import com.google.gson.stream.JsonReader
import okhttp3.Interceptor
import okhttp3.Response
import java.io.StringReader
import javax.inject.Inject

class AuthInterceptor @Inject constructor(tokenManager: TokenManager) : Interceptor {
    val token = tokenManager.getToken()
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .addHeader("Authorization","Bearer $token")
            .build()
//        request.addHeader("Authorization","Bearer $token").build()
        Log.d("NOTES ENDPOINT",token.toString())
        Log.d("NOTES ENDPOINT",request.toString())

        return chain.proceed(request)
    }
}