package id.itborneo.ugithub.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.itborneo.ugithub.core.local.FavoriteModel
import id.itborneo.ugithub.core.repository.MainRepository

class FavoriteViewModel(repo: MainRepository) : ViewModel() {
    var favorites: LiveData<List<FavoriteModel>> = repo.getFavorites()
}