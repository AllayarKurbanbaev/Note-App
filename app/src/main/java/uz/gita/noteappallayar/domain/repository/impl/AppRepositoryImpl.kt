package uz.gita.noteappallayar.domain.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.noteappallayar.data.local.dao.NoteDao
import uz.gita.noteappallayar.data.local.entity.NoteEntity
import uz.gita.noteappallayar.domain.repository.AppRepository
import javax.inject.Inject


class AppRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : AppRepository {
    override fun getAllNotes(): Flow<Result<List<NoteEntity>>> =
        flow<Result<List<NoteEntity>>> {
            val response = dao.getAllNotes()
            emit(Result.success(response))
        }.flowOn(Dispatchers.IO)

    override fun deleteNote(data: NoteEntity): Flow<Result<Unit>> =
        flow<Result<Unit>> {
            val response = dao.delete(data)
            emit(Result.success(response))
        }.flowOn(Dispatchers.IO)

    override fun update(data: NoteEntity): Flow<Result<Unit>> =
        flow<Result<Unit>> {
            val response = dao.update(data)
            emit(Result.success(response))
        }.flowOn(Dispatchers.IO)

    override fun insert(data: NoteEntity): Flow<Result<Unit>> =
        flow<Result<Unit>> {
            val response = dao.insert(data)
            emit(Result.success(response))
        }.flowOn(Dispatchers.IO)

    override fun getById(pos: Int): Flow<Result<NoteEntity>> = flow<Result<NoteEntity>> {
        val response = dao.getById(pos)
        emit(Result.success(response))
    }.flowOn(Dispatchers.IO)
}