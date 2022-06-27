package uz.gita.noteappallayar.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.noteappallayar.data.local.entity.NoteEntity

interface MainViewModel {

    val messageLiveData: LiveData<String>
    val openAddScreenLiveData: LiveData<Unit>
    val notesLiveData: LiveData<List<NoteEntity>>
    val openDetailScreenLiveData: LiveData<Int>
    val deleteNoteLiveData : LiveData<Unit>
    val openInfoDialogLiveData : LiveData<Unit>

     fun loadNotes()

     fun addNote()

     fun openDetailsScreen(pos: Int)

     fun deleteNote(data: NoteEntity)

     fun openInfoDialog()
}