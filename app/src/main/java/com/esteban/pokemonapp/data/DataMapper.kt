package com.esteban.pokemonapp.data

import com.esteban.pokemonapp.data.captured.CapturedEntity
import com.esteban.pokemonapp.data.model.*
import com.esteban.pokemonapp.data.pokemon.Move
import com.esteban.pokemonapp.data.pokemon.PokemonCommon
import com.esteban.pokemonapp.data.pokemon.PokemonEntity
import com.esteban.pokemonapp.data.team.MyTeamEntity
import com.esteban.pokemonapp.data.token.TokenEntity
import com.esteban.pokemonapp.utilities.Utils

class DataMapper {

    companion object {
        fun getLocalTokenModel(response: TokenResponse): TokenEntity {
            return TokenEntity(
                token = response.token,
                expiresAt = response.expiresAt
            )
        }

        fun getResponseTokenModel(entity: TokenEntity): TokenResponse {
            return TokenResponse(entity.token, entity.expiresAt)
        }

        fun getLocalMyTeamListModel(response: List<MyTeamResponse>): List<MyTeamEntity> {
            var list = ArrayList<MyTeamEntity>()
            for (item in response) {
                var myTeam = MyTeamEntity(
                    pokemonId = item.pokemonId,
                    chosenName = item.chosenName,
                    capturedAt = item.capturedAt,
                    capturedLatitudeAt = item.capturedLatitudeAt,
                    capturedLongitudeAt = item.capturedLongitudeAt,
                    pokemonDetails = item.pokemonDetails
                )
                list.add(myTeam)

            }

            return list
        }

        fun getLocalMyTeamModel(response: MyTeamResponse): MyTeamEntity {
            var myTeam = MyTeamEntity(
                pokemonId = response.pokemonId,
                chosenName = response.chosenName,
                capturedAt = response.capturedAt,
                capturedLatitudeAt = response.capturedLatitudeAt,
                capturedLongitudeAt = response.capturedLongitudeAt,
                pokemonDetails = response.pokemonDetails
            )
            return myTeam
        }

        fun getLocalCapturedListModel(response: CapturedResponse): List<CapturedEntity> {
            var list = ArrayList<CapturedEntity>()
            for (item in response.captured) {
                var captured = CapturedEntity(
                    id = item.pokemonId,
                    name = item.chosenName,
                    capturedAt = item.capturedAt,
                    capturedLatitudeAt = item.capturedLatitudeAt,
                    capturedLongitudeAt = item.capturedLongitudeAt,
                    pokemonDetails = item.pokemonDetails
                )
            }
            return list
        }

        fun pokemonResponseToEntity(response: PokemonResponse): PokemonEntity {
            response.moves = Utils.getRandomMoves(response.moves)
            return PokemonEntity(
                response.id,
                response.name,
                response.sprites,
                response.moves,
                response.types
            )
        }

        fun myTeamEntityToPokemonCommon(myTeamEntity: MyTeamEntity): PokemonCommon {
            return PokemonCommon(
                myTeamEntity.pokemonId,
                myTeamEntity.chosenName,
                myTeamEntity.capturedAt,
                myTeamEntity.capturedLatitudeAt,
                myTeamEntity.capturedLongitudeAt,
                myTeamEntity.pokemonDetails
            )
        }

        fun pokemonResponseToPokemonCommon(pokemonResults: PokemonResults, pokemonEntity: PokemonEntity): PokemonCommon {
            return PokemonCommon(
                pokemonEntity.id,
                pokemonEntity.name,
                "",
                pokemonResults.spottedLatitudeAt,
                pokemonResults.spottedLongitudeAt,
                pokemonEntity
            )
        }

        fun capturedEntityToPokemonCommon(capturedEntity: CapturedEntity): PokemonCommon {
            return PokemonCommon(
                capturedEntity.id,
                capturedEntity.name,
                capturedEntity.capturedAt,
                capturedEntity.capturedLatitudeAt,
                capturedEntity.capturedLongitudeAt,
                capturedEntity.pokemonDetails
            )
        }
    }
}