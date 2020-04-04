package it.alexm.dogsexample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import it.alexm.dogsexample.model.DogBreed
import it.alexm.dogsexample.model.api.DogApiService


/**
 * Created by alexm on 04/04/2020
 */
class ListViewModel : ViewModel() {

    private val dogApiService = DogApiService()
    private val disposable = CompositeDisposable()

    val dogs = MutableLiveData<List<DogBreed>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        loading.value = true

        disposable.add(
            dogApiService.fetchDogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dogs.value = it
                    error.value = false
                    loading.value = false
                }, {
                    it.printStackTrace()
                    error.value = true
                    loading.value = false
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}