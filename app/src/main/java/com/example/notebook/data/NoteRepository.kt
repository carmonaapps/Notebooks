package com.example.notebook.data

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes = noteDao.getAllNotes()
    
    suspend fun insert(note: Note) {
        noteDao.insertNote(note)
    }
    
    suspend fun delete(note: Note) {
        noteDao.deleteNote(note)
    }
    
    suspend fun update(note: Note) {
        noteDao.updateNote(note)
    }
}
