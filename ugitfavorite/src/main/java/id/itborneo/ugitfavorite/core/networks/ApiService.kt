package id.itborneo.ugitfavorite.core.networks

import id.itborneo.ugitfavorite.BuildConfig
import id.itborneo.ugitfavorite.core.model.UserDetailModel
import id.itborneo.ugitfavorite.core.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface ApiService {

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_KEY}")
    suspend fun detailUser(
        @Path("username") username: String
    ): UserDetailModel

    @GET("users/{user}/{type}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_KEY}")
    suspend fun listUsersInDetail(
        @Path("user") user: String,
        @Path("type") type: String
    ): List<UserModel>

}

