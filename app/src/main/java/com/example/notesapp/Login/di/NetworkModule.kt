package com.example.notesapp.Login.di

import android.content.Context
import com.example.notesapp.Login.Repository.ISigninRepository
import com.example.notesapp.Login.Repository.SigninRepository
import com.example.notesapp.Login.api.SigninAPI
import com.example.notesapp.Signup.api.SignupAPI
import com.example.notesapp.Token.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton // Specify Singleton scope if applicable
    fun provideSigninAPI(retrofitBuilder: Retrofit.Builder): SigninAPI {
        return retrofitBuilder.build().create(SigninAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesSigninRepository(service : SigninAPI,tokenManager: TokenManager) : ISigninRepository{
        return SigninRepository(service,tokenManager)
    }


}