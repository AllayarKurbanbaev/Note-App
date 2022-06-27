package uz.gita.noteappallayar.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.noteappallayar.data.local.entity.NoteEntity


interface AppRepository {

    fun getAllNotes(): Flow<Result<List<NoteEntity>>>

    fun deleteNote(data: NoteEntity): Flow<Result<Unit>>

    fun update(data: NoteEntity): Flow<Result<Unit>>

    fun insert(data: NoteEntity): Flow<Result<Unit>>

    fun getById(pos: Int): Flow<Result<NoteEntity>>
}