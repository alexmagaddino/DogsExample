package it.alexm.dogsexample.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import it.alexm.dogsexample.model.DogBreed
import it.alexm.dogsexample.model.DogDatabase
import it.alexm.dogsexample.model.Result
import kotlinx.coroutines.launch


/**
 * Created by alexm on 04/04/2020
 */
class DetailViewModel(application: Application) : BaseViewModel(application) {

    private val _result = MutableLiveData<Result>()
    private val disposable = CompositeDisposable()

    fun getDetail(dogId: Int): LiveData<Result> {

        disposable.add(
            Single.defer {
                val dog = DogDatabase(getApplication()).dogDao().getSelectedDog(dogId)
                Single.just(dog)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _result.value = Result.Success(it)
                }, {
                    it.printStackTrace()
                    Toast.makeText(getApplication(), "Errore", Toast.LENGTH_SHORT).show()
                })
        )

        /*launch {
            val dog = DogDatabase(getApplication()).dogDao().getSelectedDog(dogId)
            _result.value = Result.Success(dog)
        }*/

        return _result
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}