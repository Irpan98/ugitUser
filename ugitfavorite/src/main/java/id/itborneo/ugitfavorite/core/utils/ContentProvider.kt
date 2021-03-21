package id.itborneo.ugitfavorite.core.utils

import android.database.Cursor
import android.util.Log
import id.itborneo.ugitfavorite.core.model.FavoriteModel

object ContentProvider {

    fun mapCursorToFavorite(cursor: Cursor): FavoriteModel? {

        var favorite: FavoriteModel? = null
        try {
            favorite = FavoriteModel(
                cursor.getInt(cursor.getColumnIndex(DatabaseConstant.ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstant.LOGIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstant.COMPANY)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstant.PUBLIC_REPOS)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstant.EMAIL)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstant.FOLLOWERS)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstant.AVATAR_URL)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstant.HTML_URL)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstant.FOLLOWING)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstant.NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstant.LOCATION)),
            )
        } catch (throwable: Throwable) {
            Log.e("ContentProvider", "mapCursorToFavorite $throwable")
        }
        cursor.moveToNext()

        return favorite

    }
}