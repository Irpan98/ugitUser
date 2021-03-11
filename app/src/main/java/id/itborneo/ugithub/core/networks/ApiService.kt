package id.itborneo.ugithub.core.networks

import id.itborneo.ugithub.core.model.SearchResponse
import id.itborneo.ugithub.core.model.UserDetailModel
import id.itborneo.ugithub.core.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    companion object {
        const val USER_TOKEN = "b48e0b22dcfbc43d417c0d5b3bc83e9118900a5f"
    }

    //list users
    @GET("users")
    @Headers("Authorization: token $USER_TOKEN")
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

