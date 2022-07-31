package kumparan.techtest.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class ApiConfig{
    fun getApiService(): ApiService{
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @GET("posts")
    fun getPosts(): Call<List<GetPostsResponseItem>>

    @GET("users")
    fun getUsers(
        @Query("id") id: Int
    ): Call<List<GetUsersResponseItem>>

    @GET("posts/{postId}/comments")
    fun getComments(
        @Path("postId") postId: Int
    ): Call<List<GetCommentsResponseItem>>

    @GET("users/{userId}/albums")
    fun getAlbums(
        @Path("userId") userId: Int
    ): Call<List<GetAlbumsResponseItem>>

    @GET("photos")
    fun getPhotos(
        @Query("albumId") albumId: Int
    ): Call<List<GetPhotosResponseItem>>
}
