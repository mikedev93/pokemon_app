package com.esteban.pokemonapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.esteban.pokemonapp.data.captured.CapturedDao
import com.esteban.pokemonapp.data.captured.CapturedEntity
import com.esteban.pokemonapp.data.pokemon.PokemonDao
import com.esteban.pokemonapp.data.pokemon.PokemonEntity
import com.esteban.pokemonapp.data.team.MyTeamDao
import com.esteban.pokemonapp.data.team.MyTeamEntity
import com.esteban.pokemonapp.data.token.TokenDao
import com.esteban.pokemonapp.data.token.TokenEntity

@Database(entities = [MyTeamEntity::class, PokemonEntity::class, TokenEntity::class, CapturedEntity::class], version = 1, exportSchema = false)
@TypeConverters(CustomTypeConverters::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
    abstract fun myTeamDao(): MyTeamDao
    abstract fun tokenDao(): TokenDao
    abstract fun capturedDao(): CapturedDao

    companion object {
        private const val dbName = "pokemon_database"

        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    dbName)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}