package id.itborneo.ugithub.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.itborneo.ugithub.core.local.FavoriteModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.Resource
import kotlinx.coroutines.Dispatchers

class FavoriteViewModel(private val repo: MainRepository) : ViewModel() {

    var favorites: LiveData<Resource<List<FavoriteModel>>> = favorites()

    val query = MutableLiveData<String>()
    private val TAG = "HomeViewModel"

    private fun favorites() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repo.getFavorites()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}