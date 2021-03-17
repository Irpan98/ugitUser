package id.itborneo.ugithub.core.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteModel(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = FavoriteProperty.LOGIN) val login: String,
    @ColumnInfo(name = FavoriteProperty.COMPANY) val company: String,
    @ColumnInfo(name = FavoriteProperty.PUBLIC_REPOS) val publicRepos: String,
    @ColumnInfo(name = FavoriteProperty.EMAIL) val email: String,
    @ColumnInfo(name = FavoriteProperty.FOLLOWERS) val followers: Int,
    @ColumnInfo(name = FavoriteProperty.AVATAR_URL) val avatarUrl: String,
    @ColumnInfo(name = FavoriteProperty.HTML_URL) val htmlUrl: String,
    @ColumnInfo(name = FavoriteProperty.FOLLOWING) val following: Int,
    @ColumnInfo(name = FavoriteProperty.NAME) val name: String,
    @ColumnInfo(name = FavoriteProperty.LOCATION) val location: String
)