package com.example.notesapp.Login.model

import com.example.notesapp.Signup.model.SignupResult

sealed class SigninResult {
    data object Success : SigninResult()
    data object Loading : SigninResult()
    data class Error(val message: String) : SigninResult()
}