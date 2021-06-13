package com.esteban.pokemonapp.data.team

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyTeamDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewTeamMember(teamMember: MyTeamEntity)

    @Update
    suspend fun updateTeamMember(eamMember: MyTeamEntity)

    @Delete
    suspend fun deleteTeamMember(eamMember: MyTeamEntity)

    @Query("DELETE FROM my_team_table")
    suspend fun deleteAllTeamMembers()

    @Query("SELECT * FROM my_team_table")
    fun getAllTeamMembers(): LiveData<List<MyTeamEntity>>
}