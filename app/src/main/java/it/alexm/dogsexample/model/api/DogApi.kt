package it.alexm.dogsexample.model.api

import io.reactivex.rxjava3.core.Single
import it.alexm.dogsexample.model.DogBreed
import retrofit2.http.GET


/**
 * Created by alexm on 04/04/2020
 */
interface DogApi {
    @GET("DevTides/DogsApi/master/dogs.json")
//    fun fetchDogs(): Single<List<DogBreed>>
    suspend fun fetchDogs(): List<DogBreed>
}