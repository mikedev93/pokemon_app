package com.esteban.pokemonapp.network

import retrofit2.Response

abstract class BaseDataSource {

    suspend fun <T> getData(call: suspend () -> Response<T>): ResultData<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ResultData.success(body)
            }
            return formatError(" ${response.code()} ${response.message()}")
        } catch (exception: Exception) {
            return formatError(exception.message )
        }
    }

    private fun <T> formatError(errorMessage: String?): ResultData<T> {
        return ResultData.failure("Network call has failed for the following reason: $errorMessage")
    }

}