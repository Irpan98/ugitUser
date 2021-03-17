package id.itborneo.ugitfavorite.utils

import id.itborneo.ugitfavorite.FavoriteModel
import id.itborneo.ugitfavorite.UserModel

object DataMapperModel {

    fun singleFavoriteToUser(item: FavoriteModel) =
        UserModel(
            login = item.login,
            id = item.id
        )

//    fun singleDetailUserToFavorite(item: UserDetailModel) =
//        FavoriteModel(
//            item.id ?: 0,
//            item.login ?: "",
//            item.company ?: "",
//            item.reposUrl ?: "",
//            item.email ?: "",
//            item.followers ?: 0,
//            item.avatarUrl ?: "",
//            item.htmlUrl ?: "",
//            item.following ?: 0,
//            item.name ?: "",
//            item.location ?: ""
//        )
}