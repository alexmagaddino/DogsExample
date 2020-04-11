package it.alexm.dogsexample.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import it.alexm.dogsexample.model.Result
import it.alexm.dogsexample.model.db.DogDatabase


/**
 * Created by alexm on 04/04/2020
 */
class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val disposable = CompositeDisposable()

    fun getDetail(dogId: Int): LiveData<Result> {

        val liveResult = MutableLiveData<Result>()
//
//        viewModelScope.launch {
//            val dog = DogDatabase(getApplication())
//                .dogDao().getSelectedDog(dogId)
//            liveResult.value = Result.Success(dog)
//        }

        disposable.add(
            Single.defer {
                val dog = DogDatabase(getApplication()).dogDao().getSelectedDog(dogId)
                Single.just(dog)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    liveResult.value = Result.Success(it)
                }, {
                    it.printStackTrace()
                    Toast.makeText(getApplication(), "Errore", Toast.LENGTH_SHORT).show()
                })
        )

        return liveResult
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}