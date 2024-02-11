package com.example.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.ui.theme.Pink40
import com.example.noteapp.ui.theme.Pink80
import com.example.noteapp.ui.theme.Purple40
import com.example.noteapp.ui.theme.Purple80
import com.example.noteapp.ui.theme.PurpleGrey40
import com.example.noteapp.ui.theme.PurpleGrey80
import java.lang.Exception

@Entity
data class Note(
    val title : String,
    val content : String,
    val timestamp: Long,
    val color : Int,
    @PrimaryKey val id : Int? =null
){
    companion object{
        val noteColors = listOf(Purple80,PurpleGrey80,Pink80,Purple40,PurpleGrey40,Pink40)
    }
}

class InvalidNoteException(message: String):Exception(message)
