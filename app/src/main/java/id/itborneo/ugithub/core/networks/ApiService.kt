package id.itborneo.ugithub.core.networks

import id.itborneo.ugithub.BuildConfig
import id.itborneo.ugithub.core.model.UserDetailModel
import id.itborneo.ugithub.core.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface ApiService {

    @GET("users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_KEY}")
    suspend fun getUsers(): List<UserModel>

    @GET("users/{username}")
    suspend fun detailUser(
        @Path("username") username: String
    ): UserDetailModel

}

