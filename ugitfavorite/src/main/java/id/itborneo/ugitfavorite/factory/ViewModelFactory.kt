package id.itborneo.ugitfavorite.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.itborneo.ugitfavorite.DetailViewModel
import id.itborneo.ugitfavorite.ListInDetailViewModel
import id.itborneo.ugitfavorite.UserModel
import id.itborneo.ugitfavorite.repository.MainRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: MainRepository,
    private val any: Any? = null,
    private val any2: Any? = null,

    ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java))
            return DetailViewModel(repository, any as UserModel) as T
        if (modelClass.isAssignableFrom(ListInDetailViewModel::class.java))
            return ListInDetailViewModel(repository, any as String, any2 as String) as T
        throw IllegalArgumentException()

    }

}