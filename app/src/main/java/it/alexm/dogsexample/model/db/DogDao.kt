package it.alexm.dogsexample.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import it.alexm.dogsexample.model.DogBreed


/**
 * Created by alexm on 05/04/2020
 */
@Dao
interface DogDao {

    @Insert
    suspend fun insertAll(vararg dogs: DogBreed): List<Long>

    @Query("SELECT * FROM DogBreed")
    suspend fun getAllDogs(): List<DogBreed>

    @Query("SELECT * FROM DOGBREED WHERE UUID = :uuid")
    /*suspend*/ fun getSelectedDog(uuid: Int) : DogBreed

    @Query("DELETE FROM DOGBREED")
    suspend fun clear()
}