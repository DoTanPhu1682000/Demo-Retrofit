package com.dotanphu.demoretrofit.network

import com.dotanphu.demoretrofit.model.CommentItem
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.*

interface CommentService {

    @GET("comments/")
    fun getAllComment(): Call<List<CommentItem>>

    @GET("comments/")
    suspend fun getAllCommentWithCoroutines(): List<CommentItem>

    @GET("comments/")
    fun getAllCommentWithRx(): Observable<List<CommentItem>>

    @GET("comments/{id}")
    @Headers("Content-Type: application/json", "Content-Length: 15402")
    fun getCommentById(@Path("id") postId: String): Call<CommentItem>

    @POST("posts")
    fun addComment(@Header("Content-Type") contentType: String, @Body comment: CommentItem)

    //    https://jsonplaceholder.cypress.io/comment?user_id=5&field=id&sort=desc
    @GET("comments")
    suspend fun getCommentsWithQuery(
        @Query("user_id") userId: Int,
        @Query("field") searchField: String,
        @Query("sort") sortType: String
    ): List<CommentItem>

    @PUT("comment/{id}")
    suspend fun updateComment(@Path("id") id: Int, @Body comment: CommentItem)

    @PATCH("comment/{id}")
    suspend fun updateComment2(@Path("id") id: Int, @Body comment: CommentItem)

    @DELETE("comment/{id}")
    suspend fun deleteComment(@Path("id") id: Int)
}