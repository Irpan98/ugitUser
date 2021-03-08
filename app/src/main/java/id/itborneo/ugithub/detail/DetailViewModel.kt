package id.itborneo.ugithub.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.itborneo.ugithub.core.model.UserDetailModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.Resource
import kotlinx.coroutines.Dispatchers

class DetailViewModel : ViewModel() {
    lateinit var username: String
    lateinit var detailUser: LiveData<Resource<UserDetailModel>>

    private val mainRepository = MainRepository()


    fun getDetailUser(username: String) {
        detailUser = liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = mainRepository.getDetailUser(username)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }
}


