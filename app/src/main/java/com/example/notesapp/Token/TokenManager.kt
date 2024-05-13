package com.example.notesapp.Token

import android.content.Context
import androidx.compose.runtime.ProvidableCompositionLocal
import com.example.notesapp.utils.Constants.prefs_token_file
import com.example.notesapp.utils.Constants.user_token
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private val prefs = context.getSharedPreferences(prefs_token_file,Context.MODE_PRIVATE)

    fun saveToken(token : String){
        val editor = prefs.edit()
        editor.putString(user_token,token)
        editor.apply()
    }

    fun getToken() : String?{
        return prefs.getString(user_token,null)
    }

}