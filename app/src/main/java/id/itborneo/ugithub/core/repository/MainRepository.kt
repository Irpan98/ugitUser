package id.itborneo.ugithub.core.repository

import id.itborneo.ugithub.core.networks.ApiConfig.apiService

class MainRepository() {

    private val api = apiService

    suspend fun getUsers() = api.getUsers()

}