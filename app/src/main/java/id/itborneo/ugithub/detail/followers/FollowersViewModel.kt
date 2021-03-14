package id.itborneo.ugithub.detail.followers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.Resource
import kotlinx.coroutines.Dispatchers

class FollowersViewModel(private val repo: MainRepository) : ViewModel() {

    private val TAG = "FollowersViewModel"
    var users: LiveData<Resource<List<UserModel>>> = listInDetail()
    var user = ""
    var type = ""


    private fun listInDetail() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repo.getListInDetail(user, type)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}