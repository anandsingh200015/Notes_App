package com.example.notesapp.Notes.Repo

import android.util.Log
import com.example.notesapp.Notes.api.AuthInterceptor
import com.example.notesapp.Notes.api.NotesAPI
import com.example.notesapp.Notes.db.NoteDAO
import com.example.notesapp.Notes.db.NotesDb
import com.example.notesapp.Notes.model.NotesRequest
import com.example.notesapp.Notes.model.NotesResult
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject


interface INotesRepository {
    suspend fun getNotes(): NotesResult
    suspend fun saveNotes(notesRequest: NotesRequest): NotesResult
    suspend fun updateNote(noteId: String, notesRequest: NotesRequest): NotesResult
    suspend fun deleteNote(noteId: String): NotesResult

}


class NotesRepo @Inject constructor(val authInterceptor: AuthInterceptor, val service: NotesAPI,
    val room : NotesDb
) :
    INotesRepository {



//    fun getRetrofitBuilder(): Retrofit {
//
//        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(authInterceptor)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .connectTimeout(60, TimeUnit.SECONDS).build()
//
//        return Retrofit.Builder()
//            .baseUrl("https://notes-auth.onrender.com")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//
//    }


//    fun getAuthToken(): NotesAPI {
//        return getRetrofitBuilder().create(NotesAPI::class.java)
//    }

    override suspend fun getNotes(): NotesResult {
        return try {
            val response = service.getNotes()
            Log.d("NotesRepo", response.body().toString())
            if (response.isSuccessful) {
                val notes = response.body() ?: emptyList()
                room.noteDao().insertNote(notes)
                NotesResult.Success(notes)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Error Saving Notes"
                val errorCode = response.code()
                when (errorCode) {
                    400 -> NotesResult.Error("Notes Does not Exists")
                    401 -> NotesResult.Error("Unauthorized")
                    else -> {
                        Log.d("NotesRepo", "Error logged $errorBody")
                        NotesResult.Error("Something Went Wrong")
                    }
                }
            }
        } catch (e: Exception) {
            NotesResult.Error(e.message.toString())
        }

    }


    override suspend fun saveNotes(notesRequest: NotesRequest): NotesResult {
        return try {
            val response = service.sendNotesRequest(notesRequest)
            Log.d("NotesRepo", response.body().toString())
            if (response.isSuccessful) {
                NotesResult.SendRequestSuccess
            } else {
                val errorBody = response.errorBody()?.string() ?: "Error Saving Notes"
                val errorCode = response.code()
                if (errorCode == 400) {
                    NotesResult.Error("Notes Does not Exists")
                } else {
                    when (errorCode) {
                        400 -> NotesResult.Error("Notes Does not Exists")
                        401 -> NotesResult.Error("Unauthorized")
                        else -> {
                            Log.d("NotesRepo", "Error logged $errorBody")
                            NotesResult.Error("Something Went Wrong")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            NotesResult.Error(e.message.toString())
        }
    }

    override suspend fun updateNote(noteId: String, notesRequest: NotesRequest): NotesResult {
        return try {
            val response = service.updateNote(noteId, notesRequest)
            Log.d("NotesRepo", response.body().toString())
            if (response.isSuccessful) {
                NotesResult.SendRequestSuccess
            } else {
                val errorBody = response.errorBody()?.string() ?: "Error Saving Notes"
                val errorCode = response.code()
                if (errorCode == 400) {
                    NotesResult.Error("Notes Does not Exists")
                } else {
                    when (errorCode) {
                        400 -> NotesResult.Error("Notes Does not Exists")
                        401 -> NotesResult.Error("Unauthorized")
                        else -> {
                            Log.d("NotesRepo", "Error logged $errorBody")
                            NotesResult.Error("Something Went Wrong")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            NotesResult.Error(e.message.toString())
        }
    }

    override suspend fun deleteNote(noteId: String): NotesResult {
        return try {
            val response = service.deleteNote(noteId)
            Log.d("NotesRepo", response.body().toString())
            if (response.isSuccessful) {
                NotesResult.SendRequestSuccess
            } else {
                val errorBody = response.errorBody()?.string() ?: "Error Deleting Notes"
                val errorCode = response.code()
                if (errorCode == 400) {
                    NotesResult.Error("Notes Does not Exists")
                } else {
                    Log.d("SignupRepo", "Error logged $errorBody")
                    NotesResult.Error("Something Went Wrong")
                }
            }
        } catch (e: Exception) {
            NotesResult.Error(e.message.toString())
        }
    }

}



