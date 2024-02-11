package com.example.noteapp.feature_note.presentaion.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private var currentNoteId : Int? = null
    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter Title..."
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter some content.."
    ))
    val noteContent: State<NoteTextFieldState> = _noteContent


    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1){
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = _noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(addEditNoteEvent: AddEditNoteEvent){
        when(addEditNoteEvent){
            is AddEditNoteEvent.EnteredTitle ->{
                _noteTitle.value = _noteTitle.value.copy(
                    text = addEditNoteEvent.value
                )
            }
            is AddEditNoteEvent.ChangeFocusTitle ->{
                _noteTitle.value = _noteTitle.value.copy(
                    isHintVisible = !addEditNoteEvent.focusState.isFocused &&
                                     noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent ->{
                _noteContent.value = _noteContent.value.copy(
                    text = addEditNoteEvent.value
                )
            }
            is AddEditNoteEvent.ChangeFocusContent ->{
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !addEditNoteEvent.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeColor ->{
                _noteColor.value = addEditNoteEvent.color
            }

            is AddEditNoteEvent.SaveNote ->{
                viewModelScope.launch {
                    try {

                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId,
                                )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e:InvalidNoteException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Couldn't saved the note"
                            )
                        )
                    }
                }
            }

        }
    }


    sealed class UiEvent{
        data class ShowSnackBar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }





}