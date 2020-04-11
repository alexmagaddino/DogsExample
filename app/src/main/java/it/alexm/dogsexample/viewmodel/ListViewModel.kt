package it.alexm.dogsexample.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.disposables.CompositeDisposable
import it.alexm.dogsexample.model.DogBreed
import it.alexm.dogsexample.model.db.DogDatabase
import it.alexm.dogsexample.model.DogSharedPrefs
import it.alexm.dogsexample.model.api.DogApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


/**
 * Created by alexm on 04/04/2020
 */
class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = DogSharedPrefs(application)

    private val refreshTime = Calendar.getInstance().run {
        set(Calendar.MINUTE, 5)
        timeInMillis
    }

    private val dogApiService = DogApiService()
    private val disposable = CompositeDisposable()

    val dogs = MutableLiveData<List<DogBreed>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(isRefresh: Boolean = false) {
        val updateTime = prefs.updateTime
        if (!isRefresh && updateTime != 0L && System.currentTimeMillis() - updateTime < refreshTime)
            fetchFromDb()
        else
            fetchFromRemote()
    }

    private fun fetchFromDb() {
        loading.value = true
        viewModelScope.launch {
            updateLiveData(
                DogDatabase(
                    getApplication()
                ).dogDao().getAllDogs()
            )
            Toast.makeText(getApplication(), "Get from Db", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFromRemote() {
        loading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dogs = dogApiService.fetchDogs()
                storeDogsLocally(dogs)

                withContext(Dispatchers.Main) {
                    Toast.makeText(getApplication(), "Get from Remote", Toast.LENGTH_SHORT).show()
                }

            } catch (t: Throwable) {
                t.printStackTrace()
                error.value = false
                loading.value = false
            }
        }

//        disposable.add(
//            dogApiService.fetchDogs()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    storeDogsLocally(it)
//                    Toast.makeText(getApplication(), "Get from Remote", Toast.LENGTH_SHORT).show()
//                }, {
//                    it.printStackTrace()
//                    error.value = true
//                    loading.value = false
//                })
//        )
    }

    private fun updateLiveData(dogList: List<DogBreed>) {
        dogs.value = dogList
        error.value = false
        loading.value = false
    }

    private fun storeDogsLocally(dogList: List<DogBreed>) {
        viewModelScope.launch {
            val dao = DogDatabase(getApplication())
                .dogDao()
            dao.clear()
            val result = dao.insertAll(*dogList.toTypedArray())
            updateLiveData(dogList.mapIndexed { i, dog ->
                dog.uuid = result[i].toInt()
                dog
            })
        }
        prefs.updateTime = System.currentTimeMillis()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}