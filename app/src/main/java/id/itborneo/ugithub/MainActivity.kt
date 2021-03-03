package id.itborneo.ugithub

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.liveData
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val mainRepository = MainRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getUsers().observe(this) {
            Log.d(TAG, "${it.status}, ${it.message} and ${it.data}")
        }
    }

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}