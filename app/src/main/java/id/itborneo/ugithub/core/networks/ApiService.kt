package id.itborneo.ugithub.core.networks

import id.itborneo.ugithub.BuildConfig
import id.itborneo.ugithub.core.model.UserDetailModel
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.model.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_KEY}")
    suspend fun users(): List<UserModel>

    @GET("users/{username}")
    suspend fun detailUser(
        @Path("username") username: String
    ): UserDetailModel

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_KEY}")
    suspend fun searchUsers(
        @Query("q") query: String
    ): UserSearchResponse

    //    https://api.github.com/users/mojombo/followers
    @GET("users/{user}/{type}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_KEY}")
    suspend fun listUsersInDetail(
        @Path("user") user: String,
        @Path("type") type: String

    ): List<UserModel>

}

