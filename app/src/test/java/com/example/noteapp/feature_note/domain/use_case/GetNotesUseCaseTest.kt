package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.data.repository.FakeNoteRepo
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class GetNotesUseCaseTest{

    private lateinit var getNotes: GetNotesUseCase

    private lateinit var fakeNoteRepo: FakeNoteRepo


//    Junit call before every single test case
    @Before
    fun setUp(){

        fakeNoteRepo = FakeNoteRepo()
        getNotes = GetNotesUseCase(fakeNoteRepo)


        val fakeNoteData = mutableListOf<Note>()

        ('a'..'z').forEachIndexed{   index, c ->
                fakeNoteData.add(
                     Note(
                         title = c.toString(),
                         content = c.toString(),
                         timestamp = index.toLong(),
                         color = index
                     )
                )
            }

        fakeNoteData.shuffle()
        runBlocking {
            fakeNoteData.forEach{fakeNoteRepo.insertNote(it)}
        }
    }


    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking{
        val note = getNotes(NoteOrder.Title(OrderType.Ascending)).first()


        for(i in 0 ..note.size - 2){
            assertThat(note[i].title).isLessThan(note[i+1].title)
        }
    }
    @Test
    fun `Order notes by title descending, correct order`() = runBlocking{
        val note = getNotes(NoteOrder.Title(OrderType.Decending)).first()


        for(i in 0 ..note.size - 2){
            assertThat(note[i].title).isGreaterThan(note[i+1].title)
        }
    }
    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking{
        val note = getNotes(NoteOrder.Title(OrderType.Ascending)).first()


        for(i in 0 ..note.size - 2){
            assertThat(note[i].timestamp).isLessThan(note[i+1].timestamp)
        }
    }
    @Test
    fun `Order notes by date descending, correct order`() = runBlocking{
        val note = getNotes(NoteOrder.Title(OrderType.Decending)).first()


        for(i in 0 ..note.size - 2){
            assertThat(note[i].timestamp).isGreaterThan(note[i+1].timestamp)
        }
    }
    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking{
        val note = getNotes(NoteOrder.Title(OrderType.Ascending)).first()


        for(i in 0 ..note.size - 2){
            assertThat(note[i].color).isLessThan(note[i+1].color)
        }
    }
    @Test
    fun `Order notes by color descending, correct order`() = runBlocking{
        val note = getNotes(NoteOrder.Title(OrderType.Decending)).first()


        for(i in 0 ..note.size - 2){
            assertThat(note[i].color).isGreaterThan(note[i+1].color)
        }
    }


}