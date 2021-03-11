package id.itborneo.ugithub.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.itborneo.ugithub.core.local.FavoriteModel
import id.itborneo.ugithub.core.repository.MainRepository

class FavoriteViewModel(private val repo: MainRepository) : ViewModel() {

    var favorites: LiveData<List<FavoriteModel>> = repo.getFavorites()

    //    private fun getFavoritesData() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(Resource.success(data = repo.getFavorites()))
//        } catch (exception: Exception) {
//            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
//        }
//    }

    fun refreshFavorites() {
//        favorites = getFavoritesData()
    }


}