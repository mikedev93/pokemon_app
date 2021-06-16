package com.esteban.pokemonapp.data.pokemon

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewPokemon(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewPokemonList(pokemon: List<PokemonEntity>)

    @Update
    suspend fun updatePokemon(pokemon: PokemonEntity)

    @Delete
    suspend fun deletePokemon(pokemon: PokemonEntity)

    @Query("DELETE FROM pokemon_table")
    suspend fun deleteAllPokemons()

    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemons(): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_table WHERE pokemon_id=:pokemonId")
    fun getSpecificPokemon(pokemonId: Int): LiveData<PokemonEntity>

    @Query("SELECT * FROM pokemon_table WHERE name =:name")
    fun getPokemonByName(name: String): LiveData<PokemonEntity>
}