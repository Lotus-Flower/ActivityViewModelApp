package meehan.matthew.activityviewmodelapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import meehan.matthew.activityviewmodelapp.databinding.FragmentCatFactBinding

class CatFactFragment : Fragment() {

    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private val viewModel: CatFactViewModel by viewModels()
    private lateinit var binding: FragmentCatFactBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatFactBinding.inflate(inflater, container, false)
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    CatFactViewModelState.Loading -> activityViewModel.state.value = MainViewModelState.Loading
                    is CatFactViewModelState.Loaded -> {
                        activityViewModel.state.value = MainViewModelState.Loaded
                        binding.catFactText.text = state.data
                    }
                    is CatFactViewModelState.Error -> activityViewModel.state.value = MainViewModelState.Error(
                        error = state.error,
                        message = state.message
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newFactButton.setOnClickListener {
            viewModel.getACatFact()
        }
    }
}