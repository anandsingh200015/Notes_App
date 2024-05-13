package com.example.notesapp.Login.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.Login.Repository.ISigninRepository
import com.example.notesapp.Login.model.SigninRequest
import com.example.notesapp.Login.model.SigninResult
import com.example.notesapp.Token.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninScreenViewModel @Inject constructor(
    private val repository: ISigninRepository
): ViewModel() {

    private val _signinState = MutableSharedFlow<SigninResult>()
    val signinState = _signinState.asSharedFlow()

    fun sendSigninRequest(email :String,password :String){
        viewModelScope.launch {
            _signinState.emit(SigninResult.Loading)

            val signinData = SigninRequest(email, password)
            _signinState.emit(repository.SigninRequest(signinData))
        }
    }

}