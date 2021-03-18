package id.itborneo.ugitfavorite.core.utils

import id.itborneo.ugitfavorite.core.model.FavoriteModel
import id.itborneo.ugitfavorite.core.model.UserModel

object DataMapperModel {

    fun singleFavoriteToUser(item: FavoriteModel) =
        UserModel(
            login = item.login,
            id = item.id
        )
}