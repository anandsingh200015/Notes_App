package com.example.notesapp.Login.Screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.notesapp.Login.model.SigninResult
import com.example.notesapp.Login.viewModels.SigninScreenViewModel
import com.example.notesapp.R

@Composable
fun LoginScreen(viewModel: SigninScreenViewModel = hiltViewModel(),navigationtoNotes : () -> Unit, navigationtoLogin :() -> Unit){
    var Signinshowloader by remember {
        mutableStateOf(false)
    }
    var SigninEmail by remember {
        mutableStateOf("")
    }
    var Signinpassword by remember {
        mutableStateOf("")
    }
    var SigninisError by remember {
        mutableStateOf("")
    }
    val Signincontext = LocalContext.current

    LaunchedEffect(Unit){
        viewModel.signinState.collect{state ->
            when(state){
                is SigninResult.Error -> {
                    Signinshowloader = false
                    SigninisError = state.message
                    Log.d("ErrorTAG",state.message)
                }
                SigninResult.Loading -> {
                    Signinshowloader = true
                }
                SigninResult.Success -> {
                    Signinshowloader = false
                    navigationtoNotes()
                }

            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = "Welcome Back to Our Notes App",
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(value = SigninEmail,
                onValueChange = { SigninEmail = it },
                label = { Text("Email") },
                placeholder = { Text("Enter your Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                leadingIcon = {
                    Icon(
                        painterResource(R.drawable.baseline_email_24),
                        contentDescription = "Leading Email",
                        tint = Color.Blue,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(value = Signinpassword,
                onValueChange = { Signinpassword = it },
                label = { Text("Password") },
                placeholder = { Text("Enter your password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                leadingIcon = {
                    Icon(
                        painterResource(R.drawable.baseline_password_24),
                        contentDescription = "Leading Email",
                        tint = Color.Blue,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            )

            Text(
                text = SigninisError,
                fontSize = 12.sp,
                modifier = Modifier.padding(10.dp)
            )

            Button(onClick = {

                if (SigninEmail.isNullOrEmpty() || Signinpassword.isNullOrEmpty()) {
                    Toast.makeText(Signincontext, "Please Enter these details..", Toast.LENGTH_SHORT)
                        .show()
                } else if (Signinpassword.length <= 7) {
                    Toast.makeText(
                        Signincontext,
                        "Please enter atleast 8 digit password",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    viewModel.sendSigninRequest(SigninEmail, Signinpassword)
                }

            })
            {
                Text("Submit")
            }


            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(5.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = stringResource(R.string.click_here_to_signup),
                modifier = Modifier.clickable {
                    navigationtoLogin()
                })



            if (Signinshowloader) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                }
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp),
                    color = Color.Green,
                    strokeWidth = Dp(4f)
                )
            }

        }

    }
}












