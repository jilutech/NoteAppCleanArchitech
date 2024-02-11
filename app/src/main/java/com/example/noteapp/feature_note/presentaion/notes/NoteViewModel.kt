package com.example.noteapp.feature_note.presentaion.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCases
) : ViewModel(){


    private val _state = mutableStateOf(NoteState())
    val state : State<NoteState> = _state

    private var recentlyDeleteNote : Note? = null

    private var getNotesJob: Job? = null

    init {
        getNote(NoteOrder.Date(OrderType.Decending))
    }
    fun onEvent(notesEvent: NotesEvent){
        when(notesEvent){
            is NotesEvent.Order ->{

                if (state.value.noteOrder::class == notesEvent.noteOrder::class
                    &&
                    state.value.noteOrder.orderType == notesEvent.noteOrder.orderType){
                     return
                }

                getNote(notesEvent.noteOrder)
            }
            is NotesEvent.DeleteNote ->{

                viewModelScope.launch {
                    noteUseCase.deleteNote(notesEvent.note)
                    recentlyDeleteNote = notesEvent.note
                }
            }
            is NotesEvent.RestoreNote ->{

                viewModelScope.launch {

                    noteUseCase.addNote(recentlyDeleteNote ?:return@launch)
                    recentlyDeleteNote = null

                }
            }
            is NotesEvent.ToggleOrderSelection ->{

                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNote(noteOrder: NoteOrder) {

        getNotesJob?.cancel()
        noteUseCase.getNotes.invoke(noteOrder)
            .onEach { note ->
                _state.value = state.value.copy(
                    noteOrder = noteOrder,
                    notes = note
                )
            }.launchIn(viewModelScope)

    }


}