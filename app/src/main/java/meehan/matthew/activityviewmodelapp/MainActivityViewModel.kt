package meehan.matthew.activityviewmodelapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivityViewModel : ViewModel() {
    val state: MutableStateFlow<MainViewModelState?> = MutableStateFlow(null)
}