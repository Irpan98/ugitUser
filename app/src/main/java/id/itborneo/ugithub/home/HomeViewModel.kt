package id.itborneo.ugithub.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repo: MainRepository) : ViewModel() {

    var users: LiveData<Resource<List<UserModel>>> = users()
//    var userSearched: LiveData<Resource<SearchResponse>> = searchUser()


    val query = MutableLiveData<String>()
    private val TAG = "HomeViewModel"

    private fun users() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repo.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}