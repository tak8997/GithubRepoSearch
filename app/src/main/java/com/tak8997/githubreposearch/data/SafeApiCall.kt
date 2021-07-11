package com.tak8997.githubreposearch.data

suspend fun <T : Any> safeApiCall(call: suspend () -> T): Result<T> {
    return try {
        val response = call()
        Result.Success(response)
    } catch (ex: Exception) {
        Result.Error(ex)
    }
}