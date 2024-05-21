package com.example.notesapp.Notes.di

import com.example.notesapp.Notes.Repo.NotesRepo
import com.example.notesapp.Notes.api.AuthInterceptor
import com.example.notesapp.Notes.api.NotesAPI
import com.example.notesapp.Notes.db.NotesDb
import com.example.notesapp.Token.TokenManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

     @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager : TokenManager) : AuthInterceptor{
        return AuthInterceptor(tokenManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor) : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideNotesRepo(authInterceptor: AuthInterceptor, service : NotesAPI,room : NotesDb) : NotesRepo{
        return NotesRepo(authInterceptor,service,room)
    }

    @Provides
    @Singleton
    fun providesNotesAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient) : NotesAPI{
        val gson = GsonBuilder().setLenient().create()
    return retrofitBuilder.client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build().create(NotesAPI::class.java)
    }



}