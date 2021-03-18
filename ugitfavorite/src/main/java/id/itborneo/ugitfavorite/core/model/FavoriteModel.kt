package id.itborneo.ugitfavorite.core.model

data class FavoriteModel(
    val id: Int,
    val login: String,
    val company: String,
    val publicRepos: String,
    val email: String,
    val followers: Int,
    val avatarUrl: String,
    val htmlUrl: String,
    val following: Int,
    val name: String,
    val location: String
)