package id.itborneo.ugithub.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {

    var users: LiveData<Resource<List<UserModel>>> = users()

    private val mainRepository = MainRepository()

    private fun users() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun searchUser(query: String) {

        users = liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                val itemsUser = mainRepository.searchUser(query).items
                emit(Resource.success(data = itemsUser))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }

}