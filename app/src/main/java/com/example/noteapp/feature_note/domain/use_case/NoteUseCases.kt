package com.example.noteapp.feature_note.domain.use_case

data class NoteUseCases(
    val getNotes : GetNotesUseCase,
    val deleteNote: DeleteNote,
    val addNote : AddNotes,
    val getNote : GetNote
)
