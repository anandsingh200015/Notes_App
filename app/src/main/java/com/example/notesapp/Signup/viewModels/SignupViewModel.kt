package com.example.notesapp.Signup.viewModels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.Signup.Repository.ISignupRepository
import com.example.notesapp.Signup.model.SignupRequest
import com.example.notesapp.Signup.model.SignupResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: ISignupRepository
) : ViewModel(){

    private val _loginState = MutableSharedFlow<SignupResult>()
    val loginState = _loginState.asSharedFlow()



    fun sendSignupRequest(email : String, password : String, username : String) {
        viewModelScope.launch {
                _loginState.emit(SignupResult.Loading)
                val loginData  = SignupRequest(email, password, username)

                    _loginState.emit(repository.signupRequest(loginData))

        }
    }



}
