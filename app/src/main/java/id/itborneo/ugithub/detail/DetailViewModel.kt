package id.itborneo.ugithub.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.itborneo.ugithub.core.local.FavoriteModel
import id.itborneo.ugithub.core.model.UserDetailModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.DataMapperModel
import id.itborneo.ugithub.core.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MainRepository) : ViewModel() {

    private val TAG = "DetailViewModel"
    lateinit var detailUser: LiveData<Resource<UserDetailModel>>
     var isFromFavoriteFragment = false


    var isFavorite = MutableLiveData(false)
    private var favorite: FavoriteModel? = null

    fun getDetailUser(username: String) {
        detailUser = liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = repository.getDetailUser(username)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }

    }

    fun checkIsFavorite(id: Int) {
        CoroutineScope(GlobalScope.coroutineContext).launch {
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
        CoroutineScope(GlobalScope.coroutineContext).launch {
            repository.addFavorite(
                DataMapperModel.singleDetailUserToFavorite(user)
            )
            isFavorite.postValue(true)
        }

    fun removeFavorite() {
        CoroutineScope(GlobalScope.coroutineContext).launch {
            if (favorite != null) {
                repository.removeFavorite(favorite!!)
            } else {
                Log.d(TAG, "removeFavorite error")
            }
            isFavorite.postValue(false)
        }
    }

}


