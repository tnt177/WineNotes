package com.example.winenotes.database

import android.provider.ContactsContract
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun addNote(note : NOTE) : Long

    @Update
    fun updateNote(note: NOTE)

    @Delete
    fun deleteNote(note: NOTE)

    @Query("SELECT * FROM NOTE")
    fun getAllNotes(): List<NOTE>

    @Query("SELECT * FROM NOTE ORDER BY title")
    fun sortNotesBytitle(): List<NOTE>

    @Query("SELECT * FROM NOTE WHERE id = :noteId")
    fun getNote(noteId : Long) : NOTE
}