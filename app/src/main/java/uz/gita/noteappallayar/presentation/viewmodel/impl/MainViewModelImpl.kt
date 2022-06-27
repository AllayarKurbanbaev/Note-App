package uz.gita.noteappallayar.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.noteappallayar.data.local.entity.NoteEntity
import uz.gita.noteappallayar.domain.repository.AppRepository
import uz.gita.noteappallayar.presentation.viewmodel.MainViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : ViewModel(), MainViewModel {
    override val messageLiveData = MutableLiveData<String>()
    override val openAddScreenLiveData = MutableLiveData<Unit>()
    override val notesLiveData = MutableLiveData<List<NoteEntity>>()
    override val openDetailScreenLiveData = MutableLiveData<Int>()
    override val deleteNoteLiveData = MutableLiveData<Unit>()
    override val openInfoDialogLiveData = MutableLiveData<Unit>()


    override fun loadNotes() {
        repository.getAllNotes().onEach {
            it.onSuccess { notes ->
                notesLiveData.value = notes
            }
        }.launchIn(viewModelScope)
    }

    override fun addNote() {
        openAddScreenLiveData.value = Unit
    }

    override fun openDetailsScreen(pos: Int) {
        openDetailScreenLiveData.value = pos
    }

    override fun deleteNote(data: NoteEntity) {

        repository.deleteNote(data).onEach { notes ->
            notes.onSuccess {
                deleteNoteLiveData.value = Unit
            }
        }.launchIn(viewModelScope)
    }

    override fun openInfoDialog() {
        openInfoDialogLiveData.value = Unit
    }
}