package com.upiiz.jscb_examen_2.Network

import androidx.room.Query
import com.upiiz.jscb_examen_2.Models.Comment
import com.upiiz.jscb_examen_2.Models.Post
import com.upiiz.jscb_examen_2.Models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("posts")
    suspend fun getPostsByUserId(@retrofit2.http.Query("userId") userId: Int): List<Post>

    @GET("posts/{postId}/comments")
    suspend fun getCommentsByPostId(@Path("postId") postId: Int): List<Comment>

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
