package com.esteban.pokemonapp.data.pokemon

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CustomTypeConverters {

    private val typeMoves: Type = object : TypeToken<List<Move?>?>() {}.type
    private val typeSprites: Type = object : TypeToken<List<Move?>?>() {}.type
    private val typePokemonTypes: Type = object : TypeToken<List<PokemonType?>?>() {}.type
    private val typePokemonNestedType: Type = object : TypeToken<List<NestedType?>?>() {}.type

    @TypeConverter
    fun stringToMoves(json: String): List<Move> {
        return Gson().fromJson(json, typeMoves)
    }

    @TypeConverter
    fun movesToString(moves: List<Move>): String {
        return Gson().toJson(moves, typeMoves)
    }

    @TypeConverter
    fun stringToPokemonTypes(json: String): List<PokemonType> {
        return Gson().fromJson(json, typePokemonTypes)
    }

    @TypeConverter
    fun pokemonTypesToString(pokemonTypes: List<PokemonType>): String {
        return Gson().toJson(pokemonTypes, typePokemonTypes)
    }

    @TypeConverter
    fun stringToPokemonNestedType(json: String): List<NestedType> {
        return Gson().fromJson(json, typePokemonNestedType)
    }

    @TypeConverter
    fun pokemonNestedTypeToString(pokemonTypes: List<NestedType>): String {
        return Gson().toJson(pokemonTypes, typePokemonNestedType)
    }
}