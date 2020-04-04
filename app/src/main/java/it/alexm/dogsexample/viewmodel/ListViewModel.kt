package it.alexm.dogsexample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.alexm.dogsexample.model.DogBreed


/**
 * Created by alexm on 04/04/2020
 */
class ListViewModel : ViewModel() {

    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        val dogsList = arrayListOf(
            DogBreed(
                "1", "Corgi",
                "15", "breedGroup", "bredFor",
                "temperament", null
            ), DogBreed(
                "2", "Rothwailer",
                "10", "breedGroup", "bredFor",
                "temperament", null
            ), DogBreed(
                "3", "Labrador",
                "20", "breedGroup", "bredFor",
                "temperament", null
            )
        )

        dogs.value = dogsList
        dogsError.value = false
        loading.value = false
    }
}