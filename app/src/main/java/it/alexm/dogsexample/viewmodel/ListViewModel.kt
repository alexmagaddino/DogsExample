package it.alexm.dogsexample.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import it.alexm.dogsexample.model.DogBreed
import it.alexm.dogsexample.model.DogDatabase
import it.alexm.dogsexample.model.api.DogApiService
import kotlinx.coroutines.launch


/**
 * Created by alexm on 04/04/2020
 */
class ListViewModel(application: Application) : BaseViewModel(application) {

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
                    storeDogsLocally(it)
                }, {
                    it.printStackTrace()
                    error.value = true
                    loading.value = false
                })
        )
    }

    private fun updateLiveData(dogList: List<DogBreed>) {
        dogs.value = dogList
        error.value = false
        loading.value = false
    }

    private fun storeDogsLocally(dogList: List<DogBreed>) {
        launch {
            val dao = DogDatabase(getApplication()).dogDao()
            dao.clear()
            val result = dao.insertAll(*dogList.toTypedArray())
            updateLiveData(dogList.mapIndexed { i, dog ->
                dog.uuid = result[i].toInt()
                dog
            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}