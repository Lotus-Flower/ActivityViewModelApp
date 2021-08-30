package meehan.matthew.activityviewmodelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import meehan.matthew.activityviewmodelapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObservers()
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    MainViewModelState.Loading -> startLoading()
                    MainViewModelState.Loaded -> loadingComplete()
                    is MainViewModelState.Error -> showError(
                        error = state.error,
                        errorMessage = state.message
                    )
                }
            }
        }
    }

    private fun startLoading() {
        binding.apply {
            navHostFragment.visibility = View.GONE
            loadingIndicator.visibility = View.VISIBLE
        }
    }

    private fun loadingComplete() {
        binding.apply {
            navHostFragment.visibility = View.VISIBLE
            loadingIndicator.visibility = View.GONE
        }
    }

    private fun showError(error: String, errorMessage: String) {
        binding.apply {
            navHostFragment.visibility = View.GONE
            loadingIndicator.visibility = View.GONE
            errorTextHeader.visibility = View.VISIBLE
            errorTextBody.apply {
                visibility = View.VISIBLE
                text = String.format(
                    getString(R.string.error_format),
                    error,
                    errorMessage
                )
            }
        }
    }
}