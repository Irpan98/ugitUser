package id.itborneo.ugithub.core.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteModel(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = DatabaseConstant.LOGIN) val login: String,
    @ColumnInfo(name = DatabaseConstant.COMPANY) val company: String,
    @ColumnInfo(name = DatabaseConstant.PUBLIC_REPOS) val publicRepos: String,
    @ColumnInfo(name = DatabaseConstant.EMAIL) val email: String,
    @ColumnInfo(name = DatabaseConstant.FOLLOWERS) val followers: Int,
    @ColumnInfo(name = DatabaseConstant.AVATAR_URL) val avatarUrl: String,
    @ColumnInfo(name = DatabaseConstant.HTML_URL) val htmlUrl: String,
    @ColumnInfo(name = DatabaseConstant.FOLLOWING) val following: Int,
    @ColumnInfo(name = DatabaseConstant.NAME) val name: String,
    @ColumnInfo(name = DatabaseConstant.LOCATION) val location: String
)