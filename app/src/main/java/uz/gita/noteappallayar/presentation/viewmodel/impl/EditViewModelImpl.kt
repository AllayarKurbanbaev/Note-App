package uz.gita.noteappallayar.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.noteappallayar.data.local.entity.NoteEntity
import uz.gita.noteappallayar.domain.repository.AppRepository
import uz.gita.noteappallayar.presentation.viewmodel.EditViewModel
import javax.inject.Inject

@HiltViewModel
class EditViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : ViewModel(), EditViewModel {
    override val saveLiveData = MutableLiveData<String>()
    override val openDetailScreenLiveData = MutableLiveData<Int>()
    override val loadDataLiveData = MutableLiveData<NoteEntity>()

    override fun back(pos: Int) {
        openDetailScreenLiveData.value = pos
    }

    override fun saveData(data: NoteEntity) {
        repository.update(data).onEach {
            it.onSuccess {
                saveLiveData.value = "Save Data!"
            }
        }.launchIn(viewModelScope)
    }

    override fun loadData(pos: Int) {
        repository.getById(pos).onEach {
            it.onSuccess { data ->
                loadDataLiveData.value = data
            }
        }.launchIn(viewModelScope)
    }
}