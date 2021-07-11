package com.tak8997.githubreposearch.data.repository

import com.tak8997.githubreposearch.data.Result
import com.tak8997.githubreposearch.data.model.Repo

interface RepoRepository {
    fun fetchRepos(query: String, page: Int): Result<List<Repo>>
}

class RepoDataRepository : RepoRepository {

    override fun fetchRepos(query: String, page: Int): Result<List<Repo>> {
        return Result.Success(emptyList())
    }
}