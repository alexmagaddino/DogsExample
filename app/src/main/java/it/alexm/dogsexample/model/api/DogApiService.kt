package it.alexm.dogsexample.model.api

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import it.alexm.dogsexample.BuildConfig
import it.alexm.dogsexample.model.DogBreed
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by alexm on 04/04/2020
 */
class DogApiService {

    private val api = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(DogApi::class.java)

    fun fetchDogs(): Single<List<DogBreed>> {
        return api.fetchDogs()
    }
}