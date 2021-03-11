package id.itborneo.ugithub.core.repository

import id.itborneo.ugithub.core.local.FavoriteDao
import id.itborneo.ugithub.core.local.FavoriteModel
import id.itborneo.ugithub.core.networks.ApiConfig.apiService

class MainRepository(private val dao: FavoriteDao) {

    private val api = apiService

    suspend fun getUsers() = api.getUsers()
    suspend fun getDetailUser(username: String) = api.detailUser(username)

    fun getFavorites() = dao.getFavorites()
    fun geSingleFavorite(id: Int) = dao.getSingleFavorite(id)
    fun addFavorite(favorite: FavoriteModel) = dao.addFavorite(favorite)
    fun removeFavorite(favorite: FavoriteModel) = dao.removeFavorite(favorite)

}