package id.itborneo.ugitfavorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.itborneo.ugitfavorite.repository.MainRepository
import id.itborneo.ugitfavorite.utils.Resource
import kotlinx.coroutines.Dispatchers

class ListInDetailViewModel(
    private val repo: MainRepository,
    private val user: String,
    private val type: String
) : ViewModel() {

    var users: LiveData<Resource<List<UserModel>>> = listInDetail()

    private fun listInDetail() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repo.getListInDetail(user, type)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}