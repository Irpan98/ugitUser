package id.itborneo.ugithub.core.repository

import id.itborneo.ugithub.core.local.FavoriteDao
import id.itborneo.ugithub.core.local.FavoriteModel
import id.itborneo.ugithub.core.networks.ApiConfig.apiService

class MainRepository(private val dao: FavoriteDao) {

    private val api = apiService

    suspend fun getUsers() = api.users()
    suspend fun getDetailUser(username: String) = api.detailUser(username)
    suspend fun searchUsers(query: String) = api.searchUsers(query)
    suspend fun getListInDetail(user: String, type: String) = api.listUsersInDetail(user,type)

    fun getFavorites() = dao.getFavorites()
    fun geSingleFavorite(id: Int) = dao.getSingleFavorite(id)
    fun addFavorite(favorite: FavoriteModel) = dao.addFavorite(favorite)
    fun removeFavorite(favorite: FavoriteModel) = dao.removeFavorite(favorite)
}