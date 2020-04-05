package it.alexm.dogsexample.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.alexm.dogsexample.model.DogBreed
import it.alexm.dogsexample.model.DogDatabase
import it.alexm.dogsexample.model.Result
import kotlinx.coroutines.launch


/**
 * Created by alexm on 04/04/2020
 */
class DetailViewModel(application: Application) : BaseViewModel(application) {

    private val _result = MutableLiveData<Result>()

    fun getDetail(dogId: Int): LiveData<Result> {

        launch {
            val dog = DogDatabase(getApplication()).dogDao().getSelectedDog(dogId)
            _result.value = Result.Success(dog)
        }

        return _result
    }
}