package id.itborneo.ugithub.core.utils

import id.itborneo.ugithub.core.local.FavoriteModel
import id.itborneo.ugithub.core.model.UserDetailModel
import id.itborneo.ugithub.core.model.UserModel

object DataMapperModel {

    fun singleFavoriteToUser(item: FavoriteModel) =
        UserModel(
            login = item.login,
            id = item.id
        )

    fun singleDetailUserToFavorite(item: UserDetailModel) =
        FavoriteModel(
            item.id ?: 0,
            item.login ?: "",
            item.company ?: "",
            item.reposUrl ?: "",
            item.email ?: "",
            item.followers ?: 0,
            item.avatarUrl ?: "",
            item.htmlUrl ?: "",
            item.following ?: 0,
            item.name ?: "",
            item.location ?: ""
        )
}