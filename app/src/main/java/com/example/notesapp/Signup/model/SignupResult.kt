package com.example.notesapp.Signup.model


sealed class SignupResult {
    data object Success : SignupResult()
    data object Loading : SignupResult()
    data class Error(val message: String) : SignupResult()
}