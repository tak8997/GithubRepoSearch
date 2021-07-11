package com.tak8997.githubreposearch.data.model

import com.google.gson.annotations.SerializedName

data class Repo(
    val id: Long,
    @SerializedName("full_name")
    val repoName: String = "",
    @SerializedName("description")
    val repoDesc: String = ""
)
