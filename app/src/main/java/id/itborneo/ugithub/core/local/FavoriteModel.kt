package id.itborneo.ugithub.core.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteModel(
    @PrimaryKey val id: Int ,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "company") val company: String,
    @ColumnInfo(name = "publicRepos") val publicRepos: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "followers") val followers: Int,
    @ColumnInfo(name = "avatarUrl") val avatarUrl: String,
    @ColumnInfo(name = "htmlUrl") val htmlUrl: String,
    @ColumnInfo(name = "following") val following: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "location") val location: String
)