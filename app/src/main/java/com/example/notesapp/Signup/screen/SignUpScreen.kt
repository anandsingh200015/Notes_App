package com.example.notesapp.Signup.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.R
import com.example.notesapp.Signup.model.SignupResult
import com.example.notesapp.Signup.viewModels.SignupViewModel


@Composable
fun SignUpScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    navigationtoNotes: () -> Unit,
    navigationtoLogin: () -> Unit
) {

    var showloader by remember {
        mutableStateOf(false)
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }
    var isError by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loginState.collect { state ->
            when (state) {
                is SignupResult.Error -> {
                    showloader = false
                    isError = state.message
                    Log.d("ErrorTAG", state.message)
                }

                SignupResult.Loading -> {
                    showloader = true
                }

                SignupResult.Success -> {
                    showloader = false
                    navigationtoNotes()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = "Welcome to Notes App",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(value = email,
                    onValueChange = { email = it },
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
                OutlinedTextField(value = password,
                    onValueChange = { password = it },
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

                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    placeholder = { Text("Enter your Username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    leadingIcon = {
                        Icon(
                            painterResource(R.drawable.baseline_supervised_user_circle_24),
                            contentDescription = "Leading Email",
                            tint = Color.Blue,
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                )
                Text(
                    text = isError,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(10.dp)
                )

                Button(onClick = {

                    if (email.isNullOrEmpty() || password.isNullOrEmpty() || username.isNullOrEmpty()) {
                        Toast.makeText(context, "Please Enter these details..", Toast.LENGTH_SHORT)
                            .show()
                    } else if (password.length <= 7) {
                        Toast.makeText(
                            context,
                            "Please enter atleast 8 digit password",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {

                        viewModel.sendSignupRequest(email, password, username)
                    }

                })
                {
                    Text("Submit")
                }


                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(5.dp))
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = stringResource(R.string.click_here_to_login),
                    modifier = Modifier.clickable {
                        navigationtoLogin()
                    })


            }

        }
        if (showloader) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp),
                    color = Color.Green,
                    strokeWidth = Dp(4f)
                )
            }

        }

    }
}
















