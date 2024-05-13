package com.example.notesapp.Signup.di

import com.example.notesapp.Signup.Repository.ISignupRepository
import com.example.notesapp.Signup.Repository.SignupRepository
import com.example.notesapp.Signup.api.SignupAPI
import com.example.notesapp.Token.TokenManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideRetrofitBuilder() : Retrofit.Builder{
    return Retrofit.Builder().
        baseUrl("https://notes-auth.onrender.com")
        .addConverterFactory(GsonConverterFactory.create())
    }


    @Singleton
    @Provides
    fun provideUserAPI(retrofitBuilder: Retrofit.Builder) : SignupAPI{
    return retrofitBuilder.build().create(SignupAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideSignupRepository(service : SignupAPI, tokenManager: TokenManager) :ISignupRepository{
        return SignupRepository(service, tokenManager)
    }


}