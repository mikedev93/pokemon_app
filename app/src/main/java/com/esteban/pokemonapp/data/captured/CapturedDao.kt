package com.esteban.pokemonapp.data.captured

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CapturedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewCapturedPokemon(captured: CapturedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewCapturedPokemonList(capturedList: List<CapturedEntity>)

    @Update
    suspend fun updateCapturedPokemon(captured: CapturedEntity)

    @Delete
    suspend fun deleteCapturedPokemon(captured: CapturedEntity)

    @Query("DELETE FROM captured_table")
    suspend fun deleteAllCapturedPokemons()

    @Query("SELECT * FROM captured_table")
    fun getAllCapturedPokemons(): LiveData<List<CapturedEntity>>
}