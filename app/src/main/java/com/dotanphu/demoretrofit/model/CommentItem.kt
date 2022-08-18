package com.dotanphu.demoretrofit.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommentItem(
    @Expose
    @SerializedName("body")
    val body: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("postId")
    val postId: Int
)