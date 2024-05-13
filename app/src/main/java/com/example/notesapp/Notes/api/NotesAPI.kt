package com.example.notesapp.Notes.api

import com.example.notesapp.Notes.model.NotesRequest
import com.example.notesapp.Notes.model.NotesResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesAPI {

    @POST("notes")
    suspend fun sendNotesRequest(@Body notesRequest: NotesRequest) : Response<NotesResponseItem>

    @PUT("/notes/{noteId}")
    suspend fun updateNote(@Path("noteId") noteId : String, @Body notesRequest: NotesRequest) : Response<NotesResponseItem>

    @GET("/notes")
    suspend fun getNotes() : Response<List<NotesResponseItem>>

    @DELETE("/notes/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId: String) : Response<NotesResponseItem>

}