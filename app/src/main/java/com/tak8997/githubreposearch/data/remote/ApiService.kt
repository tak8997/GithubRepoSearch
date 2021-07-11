package com.tak8997.githubreposearch.data.remote

import com.tak8997.githubreposearch.data.model.RepoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/search/repositories")
    suspend fun fetchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): RepoResponse
}