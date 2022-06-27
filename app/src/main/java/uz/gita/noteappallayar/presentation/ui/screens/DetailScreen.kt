package uz.gita.noteappallayar.presentation.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.noteappallayar.R
import uz.gita.noteappallayar.data.local.entity.NoteEntity
import uz.gita.noteappallayar.databinding.ScreenDetailBinding
import uz.gita.noteappallayar.presentation.viewmodel.DetailViewModel
import uz.gita.noteappallayar.presentation.viewmodel.impl.DetailViewModelImpl

@AndroidEntryPoint
class DetailScreen : Fragment(R.layout.screen_detail) {

    private val binding by viewBinding(ScreenDetailBinding::bind)
    private val viewModel: DetailViewModel by viewModels<DetailViewModelImpl>()
    private val args: DetailScreenArgs by navArgs()


    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        Log.d("TTT", args.pos.toString())
        viewModel.loadData(args.pos)

        backButton.setOnClickListener {
            viewModel.onBack()
        }

        editButton.setOnClickListener {
            viewModel.editData(args.pos)
        }

        viewModel.loadDataLiveData.observe(viewLifecycleOwner, loadDataObserver)
        viewModel.onBackPressedLiveData.observe(viewLifecycleOwner, onBackPressedObserver)
        viewModel.openEditScreenLiveData.observe(this@DetailScreen, openEditScreenObserver)
    }

    private val loadDataObserver = Observer<NoteEntity> { data ->
        binding.tvtitle.text = data.title
        binding.tvDescription.text = data.description
    }
    private val onBackPressedObserver = Observer<Unit> {
        findNavController().popBackStack()
    }

    private val openEditScreenObserver = Observer<Int> {
        findNavController().navigate(DetailScreenDirections.actionDetailScreenToEditScreen(it))
    }
}