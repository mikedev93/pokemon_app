package com.esteban.pokemonapp.data.pokemon

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class CustomTypeConverters {

    private val typeMoves: Type = object : TypeToken<List<Move?>?>() {}.type
    private val typeSprites: Type = object : TypeToken<List<Move?>?>() {}.type

    @TypeConverter
    fun stringToMoves(json: String): List<Move> {
        return Gson().fromJson(json, typeMoves)
    }

    @TypeConverter
    fun movesToString(moves: List<Move>): String {
        return Gson().toJson(moves, typeMoves)
    }

    @TypeConverter
    fun stringToSprites(json: String): List<Sprites> {
        return Gson().fromJson(json, typeSprites)
    }

    @TypeConverter
    fun spritesToString(sprites: List<Sprites>): String {
        return Gson().toJson(sprites, typeSprites)
    }
}