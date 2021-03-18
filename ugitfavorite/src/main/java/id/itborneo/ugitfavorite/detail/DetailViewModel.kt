package id.itborneo.ugitfavorite.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.itborneo.ugitfavorite.core.model.UserDetailModel
import id.itborneo.ugitfavorite.core.model.UserModel
import id.itborneo.ugitfavorite.core.repository.MainRepository
import id.itborneo.ugitfavorite.core.utils.Resource
import kotlinx.coroutines.Dispatchers

class DetailViewModel(private val repository: MainRepository, val userModel: UserModel) :
    ViewModel() {


    lateinit var detailUser: LiveData<Resource<UserDetailModel>>

    init {
        getDetailUser()
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

}
