package id.itborneo.ugithub.core.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getFavorites(): LiveData<List<FavoriteModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favorite: FavoriteModel)

    @Query("SELECT * FROM favorite WHERE id=:id ")
    fun getSingleFavorite(id: Int): FavoriteModel

    @Delete
    fun removeFavorite(favorite: FavoriteModel)
}