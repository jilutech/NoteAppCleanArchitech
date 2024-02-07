package com.example.noteapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteapp.feature_note.data.data_source.NoteDB
import com.example.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.use_case.DeleteNote
import com.example.noteapp.feature_note.domain.use_case.GetNoteUseCase
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(application: Application) : NoteDB {
        return Room.databaseBuilder(
            application, NoteDB::class.java,
            NoteDB.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDB): NoteRepository{
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases{

        return NoteUseCases(
            getNotes = GetNoteUseCase(repository),
            deleteNote = DeleteNote(repository)
        )
    }

}