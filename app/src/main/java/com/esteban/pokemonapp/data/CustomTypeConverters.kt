package com.esteban.pokemonapp.data

import androidx.room.TypeConverter
import com.esteban.pokemonapp.data.pokemon.Move
import com.esteban.pokemonapp.data.pokemon.TypeSlot
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CustomTypeConverters {

    private val typeMoves: Type = object : TypeToken<List<Move?>?>() {}.type
    private val typePokemonTypes: Type = object : TypeToken<List<TypeSlot?>?>() {}.type
    private val typePokemonNestedType: Type = object : TypeToken<List<com.esteban.pokemonapp.data.pokemon.NestedType?>?>() {}.type

    @TypeConverter
    fun stringToMoves(json: String): List<Move> {
        return Gson().fromJson(json, typeMoves)
    }

    @TypeConverter
    fun movesToString(moves: List<Move>): String {
        return Gson().toJson(moves, typeMoves)
    }

    @TypeConverter
    fun stringToPokemonTypes(json: String): List<TypeSlot> {
        return Gson().fromJson(json, typePokemonTypes)
    }

    @TypeConverter
    fun pokemonTypesToString(typeSlots: List<TypeSlot>): String {
        return Gson().toJson(typeSlots, typePokemonTypes)
    }

    @TypeConverter
    fun stringToPokemonNestedType(json: String): List<com.esteban.pokemonapp.data.pokemon.NestedType> {
        return Gson().fromJson(json, typePokemonNestedType)
    }

    @TypeConverter
    fun pokemonNestedTypeToString(pokemonNestedTypes: List<com.esteban.pokemonapp.data.pokemon.NestedType>): String {
        return Gson().toJson(pokemonNestedTypes, typePokemonNestedType)
    }
}