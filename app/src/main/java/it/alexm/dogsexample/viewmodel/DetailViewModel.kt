package it.alexm.dogsexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.alexm.dogsexample.model.DogBreed
import it.alexm.dogsexample.model.Result


/**
 * Created by alexm on 04/04/2020
 */
class DetailViewModel : ViewModel() {

    private val _result = MutableLiveData<Result>()

    fun getDetail(): LiveData<Result> {
        val dog = DogBreed(
            "2", "Rothwailer",
            "10", "breedGroup", "bredFor",
            "temperament", null
        )

        _result.value = Result.Success(dog)

        return _result
    }
}