package com.example.notesapp.Notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes")
data class NotesResponseItem(
    @PrimaryKey val _id: String,
    val title: String,
    val description: String,
)