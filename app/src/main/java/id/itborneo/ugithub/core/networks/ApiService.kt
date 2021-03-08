package id.itborneo.ugithub.core.networks

import id.itborneo.ugithub.core.model.SearchResponse
import id.itborneo.ugithub.core.model.UserDetailModel
import id.itborneo.ugithub.core.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    //list users
    @GET("users")
    suspend fun getUsers(): List<UserModel>

    @GET("users/{username}")
    suspend fun detailUser(
        @Path("username") username: String
    ): UserDetailModel

    @GET("users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): SearchResponse

}

