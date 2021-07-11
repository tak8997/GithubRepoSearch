package com.tak8997.githubreposearch.data.repository

import com.tak8997.githubreposearch.data.Result
import com.tak8997.githubreposearch.data.model.Repo
import com.tak8997.githubreposearch.data.remote.ApiService
import com.tak8997.githubreposearch.data.safeApiCall

private const val PER_PAGE = 20

interface RepoRepository {
    suspend fun fetchRepos(query: String, page: Int): Result<List<Repo>>
}

class RepoDataRepository(
    private val apiService: ApiService
) : RepoRepository {

    override suspend fun fetchRepos(query: String, page: Int): Result<List<Repo>> {
        return safeApiCall { apiService.fetchRepos(query, page, PER_PAGE).repos }
    }
}