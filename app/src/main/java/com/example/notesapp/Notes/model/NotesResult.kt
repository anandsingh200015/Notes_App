package com.example.notesapp.Notes.model

import retrofit2.Response

sealed class NotesResult{
    data class Success(val notes: List<NotesResponseItem>) : NotesResult()
    data object SendRequestSuccess : NotesResult()
    data class Error(val message : String) : NotesResult()
}