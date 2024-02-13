package com.example.noteapp.feature_note.presentaion.notes.components

import android.app.Application
import androidx.room.Room
import com.example.noteapp.feature_note.data.data_source.NoteDB
import com.example.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.use_case.AddNotes
import com.example.noteapp.feature_note.domain.use_case.DeleteNote
import com.example.noteapp.feature_note.domain.use_case.GetNote
import com.example.noteapp.feature_note.domain.use_case.GetNotesUseCase
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(application: Application) : NoteDB {
        return Room.inMemoryDatabaseBuilder(
            application, NoteDB::class.java,
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
            getNotes = GetNotesUseCase(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNotes(repository),
            getNote = GetNote(repository)
        )
    }

}