package com.example.noteapp.feature_note.presentaion.util

sealed class Screen(val route: String) {
    object NoteScreen: Screen("note_screen")
    object NoteAddEditScreen: Screen("note_add_edit_screen")
}