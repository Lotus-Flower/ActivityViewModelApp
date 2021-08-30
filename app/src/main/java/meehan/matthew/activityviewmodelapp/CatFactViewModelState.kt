package meehan.matthew.activityviewmodelapp

sealed class CatFactViewModelState {
    object Loading: CatFactViewModelState()
    data class Loaded(val data: String): CatFactViewModelState()
    data class Error(val error: String, val message: String): CatFactViewModelState()
}
