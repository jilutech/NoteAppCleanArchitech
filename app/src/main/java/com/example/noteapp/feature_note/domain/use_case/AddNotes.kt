package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

class AddNotes(private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){

        if (note.title.isBlank()){

            throw InvalidNoteException("The title of the note should not be blank")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("The content of the note should not be blank")
        }
        repository.insertNote(note)

    }
}