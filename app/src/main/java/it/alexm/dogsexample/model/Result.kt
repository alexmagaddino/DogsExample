package it.alexm.dogsexample.model


/**
 * Created by alexm on 04/04/2020
 */
sealed class Result {
    object Loading : Result()

    class Error<T : Throwable>(val exception: T) : Result()

    class Success<out R>(val value: R?) : Result()
}