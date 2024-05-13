package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.Login.Screen.LoginScreen
import com.example.notesapp.Notes.Screens.NotesScreen
import com.example.notesapp.Signup.screen.SignUpScreen
import com.example.notesapp.Token.TokenManager
import com.example.notesapp.ui.theme.NotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()

                }
            }
        }
    }
    @Composable
    fun App() {

        val tokenManager = remember {
            TokenManager(applicationContext)
        }

        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "signup"){
            composable(route = "signup"){


                if (tokenManager.getToken() != null){
                    navController.navigate("notes"){(popUpTo("signup"){inclusive = true})}
                }

                SignUpScreen(navigationtoNotes={
                    navController.navigate("notes"){(popUpTo("signup"){inclusive = true})}
                },
                    navigationtoLogin = {
                        navController.navigate("login"){(popUpTo("signup"){inclusive = true})}
                    })

            }
            composable(route = "login"){
                LoginScreen(navigationtoNotes = {
                    navController.navigate("notes"){(popUpTo("login"){inclusive = true})}
                },navigationtoLogin = {
                    navController.navigate("signup"){(popUpTo("login"){inclusive = true})}
                })
            }
            composable(route = "notes"){
                NotesScreen()
            }
        }
    }

}

