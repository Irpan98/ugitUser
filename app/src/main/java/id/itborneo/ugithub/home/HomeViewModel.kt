package id.itborneo.ugithub.home

import androidx.lifecycle.*
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.model.UserSearchResponse
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: MainRepository) : ViewModel() {

    var users: LiveData<Resource<List<UserModel>>> = users()
    var usersSearched = MutableLiveData<Resource<UserSearchResponse>>()

    private fun users() = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repo.getUsers()))
        } catch (throwable: Throwable) {
            emit(Resource.error(data = null, message = throwable.message ?: "Error Occurred!"))
        }
    }

    fun searchUsers(query: String) = viewModelScope.launch(Dispatchers.IO) {
        usersSearched.postValue(Resource.loading(data = null))
        try {
            usersSearched.postValue(Resource.success(data = repo.searchUsers(query)))

        } catch (throwable: Throwable) {
            usersSearched.postValue(
                Resource.error(
                    data = null,
                    message = throwable.message ?: "Error Occurred!"
                )
            )
        }
    }

}