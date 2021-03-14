package id.itborneo.ugithub.core.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.detail.DetailViewModel
import id.itborneo.ugithub.detail.list.ListInDetailViewModel
import id.itborneo.ugithub.favorite.FavoriteViewModel
import id.itborneo.ugithub.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: MainRepository,
    private val any: Any? = null
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java))
            return DetailViewModel(repository, any as UserModel) as T
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java))
            return FavoriteViewModel(repository) as T
        if (modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(repository) as T
        if (modelClass.isAssignableFrom(ListInDetailViewModel::class.java))
            return ListInDetailViewModel(repository) as T
        throw IllegalArgumentException()
    }

}