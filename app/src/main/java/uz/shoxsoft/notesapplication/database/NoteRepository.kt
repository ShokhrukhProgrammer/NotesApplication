package uz.shoxsoft.notesapplication.database

import androidx.lifecycle.LiveData
import uz.shoxsoft.notesapplication.interfaces.NotesDao
import uz.shoxsoft.notesapplication.model.Note

class NoteRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insert(note: Note) {
        notesDao.insert(note)
    }

    suspend fun delete(note: Note) {
        notesDao.delete(note)
    }

    suspend fun update(note: Note) {
        notesDao.update(note)
    }
}