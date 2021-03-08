package id.itborneo.ugithub.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.itborneo.ugithub.core.model.SearchResponse
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {

    var users: LiveData<Resource<List<UserModel>>> = users()
//    var userSearched: LiveData<Resource<SearchResponse>> = searchUser()

    private val mainRepository = MainRepository()

    val query = MutableLiveData<String>()
    private val TAG = "HomeViewModel"

    private fun users() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

//    fun searchUser() = liveData(Dispatchers.IO) {
//        Log.d(TAG, "searchUser ${query.value}")
//        emit(Resource.loading(data = null))
//        try {
//            val itemsUser = mainRepository.searchUser(query.value ?: "")
//            emit(Resource.success(data = itemsUser))
//        } catch (exception: Exception) {
//            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred! searchUser"))
//        }
//    }




}