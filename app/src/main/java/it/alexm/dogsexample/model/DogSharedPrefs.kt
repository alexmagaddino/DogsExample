package it.alexm.dogsexample.model

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager


/**
 * Created by alexm on 05/04/2020
 */
object DogSharedPrefs {

    private var context: Context? = null

    private const val TIME_KEY = "time_key"

    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    operator fun invoke(context: Context): DogSharedPrefs {
        this.context = context
        return this
    }

    var updateTime: Long = 0
        set(value) {
            prefs.edit(commit = true) {
                putLong(TIME_KEY, value)
            }
            field = value
        }
        get() {
            return prefs.getLong(TIME_KEY, 0)
        }

}