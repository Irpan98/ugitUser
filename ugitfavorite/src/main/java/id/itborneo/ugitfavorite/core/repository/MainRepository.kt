package id.itborneo.ugitfavorite.core.repository

import id.itborneo.ugitfavorite.networks.ApiConfig.apiService

class MainRepository {
    private val api = apiService

    suspend fun getDetailUser(username: String) = api.detailUser(username)
    suspend fun getListInDetail(user: String, type: String) = api.listUsersInDetail(user, type)
}