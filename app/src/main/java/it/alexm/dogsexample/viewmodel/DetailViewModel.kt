package it.alexm.dogsexample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import it.alexm.dogsexample.model.db.DogDatabase
import it.alexm.dogsexample.model.Result
import kotlinx.coroutines.launch


/**
 * Created by alexm on 04/04/2020
 */
class DetailViewModel(application: Application) : AndroidViewModel(application) {

    fun getDetail(dogId: Int): LiveData<Result> {

        val liveResult = MutableLiveData<Result>()

        viewModelScope.launch {
            val dog = DogDatabase(getApplication())
                .dogDao().getSelectedDog(dogId)
            liveResult.value = Result.Success(dog)
        }

        return liveResult
    }
}