package id.itborneo.ugithub.detail

import android.util.Log
import androidx.lifecycle.*
import id.itborneo.ugithub.core.local.FavoriteModel
import id.itborneo.ugithub.core.model.UserDetailModel
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.DataMapperModel
import id.itborneo.ugithub.core.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MainRepository, val userModel: UserModel) :
    ViewModel() {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    lateinit var detailUser: LiveData<Resource<UserDetailModel>>
    private var favorite: FavoriteModel? = null
    private var isFromFavoriteFragment = false
    var isFavorite = MutableLiveData(false)

    init {
        getDetailUser()
        userModel.id?.let { checkIsFavorite(it) }
    }

    private fun getDetailUser() {
        detailUser = liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = repository.getDetailUser(userModel.login ?: "")))
            } catch (throwable: Throwable) {
                emit(Resource.error(data = null, message = throwable.message ?: "Error Occurred!"))
            }
        }
    }

    private fun checkIsFavorite(id: Int) {
        viewModelScope.launch {
            favorite = repository.geSingleFavorite(id)

            if (favorite == null || id == 0) {
                isFavorite.postValue(false)
            } else {
                isFromFavoriteFragment = true
                isFavorite.postValue(true)
            }
        }
    }

    fun addToFavorite(user: UserDetailModel) =
        viewModelScope.launch {
            repository.addFavorite(
                DataMapperModel.singleDetailUserToFavorite(user)
            )
            isFavorite.postValue(true)
        }

    fun removeFavorite() {
        viewModelScope.launch {
            val fav = favorite
            if (fav != null) {
                repository.removeFavorite(fav)
            } else {
                Log.e(TAG, "removeFavorite error")
            }
            isFavorite.postValue(false)
        }
    }
}
