package meehan.matthew.activityviewmodelapp

sealed class MainViewModelState {
    object Loading: MainViewModelState()
    object Loaded: MainViewModelState()
    data class Error(val error: String, val message: String): MainViewModelState()
}