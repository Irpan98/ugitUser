package id.itborneo.ugitfavorite.networks

import id.itborneo.ugitfavorite.BuildConfig
import id.itborneo.ugitfavorite.UserDetailModel
import id.itborneo.ugitfavorite.UserModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface ApiService {

    //    @GET("users")
//    @Headers("Authorization: token ${BuildConfig.GITHUB_KEY}")
//    suspend fun users(): List<UserModel>
//
    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_KEY}")

    suspend fun detailUser(
        @Path("username") username: String
    ): UserDetailModel

    //
//    @GET("search/users")
//    @Headers("Authorization: token ${BuildConfig.GITHUB_KEY}")
//    suspend fun searchUsers(
//        @Query("q") query: String
//    ): UserSearchResponse
//
    @GET("users/{user}/{type}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_KEY}")
    suspend fun listUsersInDetail(
        @Path("user") user: String,
        @Path("type") type: String

    ): List<UserModel>

}

