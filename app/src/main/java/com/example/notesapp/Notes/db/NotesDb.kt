package com.example.notesapp.Notes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.Notes.model.NotesResponseItem

@Database(entities = [NotesResponseItem::class], version = 1, exportSchema = false)
abstract class NotesDb : RoomDatabase(){
    abstract fun noteDao() : NoteDAO
}