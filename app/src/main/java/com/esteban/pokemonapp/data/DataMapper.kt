package com.esteban.pokemonapp.data

import com.esteban.pokemonapp.data.captured.CapturedEntity
import com.esteban.pokemonapp.data.model.CapturedResponse
import com.esteban.pokemonapp.data.model.MyTeamResponse
import com.esteban.pokemonapp.data.model.TokenResponse
import com.esteban.pokemonapp.data.team.MyTeamEntity
import com.esteban.pokemonapp.data.token.TokenEntity

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

        fun getLocalLocalMyTeamListModel(response: List<MyTeamResponse>): List<MyTeamEntity> {
            var list = ArrayList<MyTeamEntity>()
            for (item in response) {
                var myTeam = MyTeamEntity(
                    pokemonId = item.pokemonId,
                    chosenName = item.chosenName,
                    capturedAt = item.capturedAt,
                    capturedLatitudeAt = item.capturedLatitudeAt,
                    capturedLongitudeAt = item.capturedLongitudeAt
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
                capturedLongitudeAt = response.capturedLongitudeAt
            )
            return myTeam
        }

        fun getLocalCapturedListModel(response: CapturedResponse): List<CapturedEntity> {
            var list = ArrayList<CapturedEntity>()
            for (item in response.captured) {
                var captured = CapturedEntity(
                    id = item.id,
                    name = item.chosenName,
                    capturedAt = item.capturedAt,
                    capturedLatitudeAt = item.capturedLatitudeAt,
                    capturedLongitudeAt = item.capturedLongitudeAt
                )
            }
            return list
        }
    }
}