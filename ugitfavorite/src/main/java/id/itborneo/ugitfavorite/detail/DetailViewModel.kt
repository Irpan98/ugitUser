package id.itborneo.ugitfavorite.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.itborneo.ugitfavorite.core.model.FavoriteModel
import id.itborneo.ugitfavorite.core.model.UserDetailModel
import id.itborneo.ugitfavorite.core.model.UserModel
import id.itborneo.ugitfavorite.core.repository.MainRepository
import id.itborneo.ugitfavorite.core.utils.Resource
import kotlinx.coroutines.Dispatchers

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
//        userModel.id?.let { checkIsFavorite(it) }
    }

    private fun getDetailUser() {
        detailUser = liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = repository.getDetailUser(userModel.login ?: "")))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }

//    private fun checkIsFavorite(id: Int) {
//        CoroutineScope(GlobalScope.coroutineContext).launch {
//            favorite = repository.geSingleFavorite(id)
//
//            if (favorite == null || id == 0) {
//                isFavorite.postValue(false)
//            } else {
//                isFromFavoriteFragment = true
//                isFavorite.postValue(true)
//            }
//        }
//    }

//    fun addToFavorite(user: UserDetailModel) =
//        CoroutineScope(GlobalScope.coroutineContext).launch {
//            repository.addFavorite(
//                DataMapperModel.singleDetailUserToFavorite(user)
//            )
//            isFavorite.postValue(true)
//        }
//
//    fun removeFavorite() {
//        CoroutineScope(GlobalScope.coroutineContext).launch {
//            val fav = favorite
//            if (fav != null) {
//                repository.removeFavorite(fav)
//            } else {
//                Log.e(TAG, "removeFavorite error")
//            }
//            isFavorite.postValue(false)
//        }
//    }
}
