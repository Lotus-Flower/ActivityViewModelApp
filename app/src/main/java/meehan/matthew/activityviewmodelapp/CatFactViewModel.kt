package meehan.matthew.activityviewmodelapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class CatFactViewModel : ViewModel() {

    private val factList = listOf(
        "Purring does not always indicate that a cat is happy and healthy - some cats will purr loudly when they are terrified or in pain.",
        "Cats can be right-pawed or left-pawed.",
        "The life expectancy of cats has nearly doubled over the last fifty years.",
        "Cats don't have sweat glands over their bodies like humans do. Instead, they sweat only through their paws.",
        "Cats see six times better in the dark and at night than humans.",
        "Cats are North America's most popular pets: there are 73 million cats compared to 63 million dogs. Over 30% of households in North America own a cat.",
        "The biggest wildcat today is the Siberian Tiger. It can be more than 12 feet (3.6 m) long (about the size of a small car) and weigh up to 700 pounds (317 kg).",
        "Cats sleep 16 to 18 hours per day. When cats are asleep, they are still alert to incoming stimuli. If you poke the tail of a sleeping cat, it will respond accordingly.",
        "When a cat drinks, its tongue - which has tiny barbs on it - scoops the liquid up backwards.",
        "Normal body temperature for a cat is 102 degrees F.",
        null
    )

    private val _state: MutableStateFlow<CatFactViewModelState?> = MutableStateFlow(null)
    val state: StateFlow<CatFactViewModelState?> = _state

    init {
        getACatFact()
    }

    fun getACatFact() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = CatFactViewModelState.Loading
            delay(3000)

            val data = factList[Random.nextInt(factList.size)]
            if (data != null) {
                _state.value = CatFactViewModelState.Loaded(data)
            } else {
                _state.value = CatFactViewModelState.Error(
                    error = "Data Error",
                    message = "Cat fact received was null"
                )
            }
        }
    }
}