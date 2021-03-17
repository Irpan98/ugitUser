package id.itborneo.ugithub.core.utils

import android.database.Cursor
import android.util.Log
import id.itborneo.ugithub.core.local.FavoriteModel
import id.itborneo.ugithub.core.local.FavoriteProperty

object ContentProvider {

    fun mapCursorToFavorite(cursor: Cursor): FavoriteModel? {

        var favorite: FavoriteModel? = null
        try {
            favorite = FavoriteModel(
                cursor.getInt(cursor.getColumnIndex(FavoriteProperty.ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(FavoriteProperty.LOGIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(FavoriteProperty.COMPANY)),
                cursor.getString(cursor.getColumnIndexOrThrow(FavoriteProperty.PUBLIC_REPOS)),
                cursor.getString(cursor.getColumnIndexOrThrow(FavoriteProperty.EMAIL)),
                cursor.getInt(cursor.getColumnIndexOrThrow(FavoriteProperty.FOLLOWERS)),
                cursor.getString(cursor.getColumnIndexOrThrow(FavoriteProperty.AVATAR_URL)),
                cursor.getString(cursor.getColumnIndexOrThrow(FavoriteProperty.HTML_URL)),
                cursor.getInt(cursor.getColumnIndexOrThrow(FavoriteProperty.FOLLOWING)),
                cursor.getString(cursor.getColumnIndexOrThrow(FavoriteProperty.NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(FavoriteProperty.LOCATION)),
            )
        } catch (e: Exception) {
            Log.d("ContentProvider", "mapCursorToFavorite $e")
        }
        cursor.moveToNext()

        return favorite

    }
}