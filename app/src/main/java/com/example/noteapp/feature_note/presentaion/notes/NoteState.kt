package com.example.noteapp.feature_note.presentaion.notes

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType

data class NoteState(
    val notes : List<Note> = emptyList(),
    val noteOrder : NoteOrder = NoteOrder.Date(OrderType.Decending),
    val isOrderSectionVisible : Boolean = false
)
