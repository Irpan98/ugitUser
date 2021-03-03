package id.itborneo.blanjaa.core.source.remote.network

import id.itborneo.ugithub.core.model.UsersResponse
import retrofit2.http.GET


interface ApiService {

    //list users
    @GET("users")
    suspend fun getUsers(): UsersResponse


}

