package com.example.notesapp.Notes.model

data class NotesResponseItem(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val description: String,
    val title: String,
    val updatedAt: String,
    val userId: String
)