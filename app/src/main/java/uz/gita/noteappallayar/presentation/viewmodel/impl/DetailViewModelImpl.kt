package uz.gita.noteappallayar.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.noteappallayar.data.local.entity.NoteEntity
import uz.gita.noteappallayar.domain.repository.AppRepository
import uz.gita.noteappallayar.presentation.viewmodel.DetailViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : ViewModel(), DetailViewModel {
    override val loadDataLiveData = MutableLiveData<NoteEntity>()
    override val onBackPressedLiveData = MutableLiveData<Unit>()
    override val openEditScreenLiveData = MutableLiveData<Int>()

    override fun onBack() {
        onBackPressedLiveData.value = Unit
    }

    override fun editData(pos : Int) {
        openEditScreenLiveData.value = pos
    }


    override fun loadData(pos: Int) {
        repository.getById(pos).onEach {
            it.onSuccess { data ->
                loadDataLiveData.value = data
            }
        }.launchIn(viewModelScope)
    }
}