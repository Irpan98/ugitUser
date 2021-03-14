package id.itborneo.ugithub.home

import android.app.DownloadManager
import android.util.Log
import androidx.lifecycle.*
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.model.UserSearchResponse
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: MainRepository) : ViewModel() {

    private val TAG = "HomeViewModel"
    var users: LiveData<Resource<List<UserModel>>> = users()
    var seachedUsers= MutableLiveData<Resource<UserSearchResponse>>()
    var query = MutableLiveData("")


    private fun users() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repo.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun searchUsers(query: String) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "query $query")

        seachedUsers?.postValue(Resource.loading(data = null))
        try {
            seachedUsers?.postValue(Resource.success(data = repo.searchUsers(query ?: "")))
            Log.d(TAG, "query ${repo.searchUsers(query ?: "")}")

        } catch (exception: Exception) {
            seachedUsers?.postValue(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Error Occurred!"
                )
            )
        }


    }

}