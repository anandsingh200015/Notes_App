package com.example.notesapp.Notes.di

import android.content.Context
import androidx.room.Room
import com.example.notesapp.Notes.db.NotesDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {


    @Provides
    @Singleton
    fun provideMyDatabase(@ApplicationContext context: Context) : NotesDb {
        return Room.databaseBuilder(
            context,
            NotesDb::class.java,
            "notes_db"
        ).build()
    }

}