package com.esteban.pokemonapp.data.token

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewToken(token: TokenEntity)

    @Query("DELETE FROM token_table")
    suspend fun deleteToken()

    @Query("SELECT * FROM token_table ORDER BY id ASC LIMIT 1")
    fun getToken(): LiveData<TokenEntity>
}