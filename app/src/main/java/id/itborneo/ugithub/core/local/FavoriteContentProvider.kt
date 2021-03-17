package id.itborneo.ugithub.core.local

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.room.RoomMasterTable.TABLE_NAME
import id.itborneo.ugithub.core.utils.ContentProvider.mapCursorToFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class FavoriteContentProvider : ContentProvider() {
    companion object {

        const val AUTHORITY = "id.itborneo.ugithub"
        const val SCHEME = "content"

        const val FAVORITE = 1
    }

    private val sUriMather = UriMatcher(UriMatcher.NO_MATCH)
    private lateinit var favoriteDao: FavoriteDao

    init {
        sUriMather.addURI(AUTHORITY, TABLE_NAME, FAVORITE)
    }

    override fun onCreate(): Boolean {
        val context = context ?: return false
        favoriteDao = AppDatabase.getInstance(context).favoriteDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor {
        return runBlocking {
            withContext(Dispatchers.IO) {
                getQueryFavorite()
            }
        }

    }

    private fun getQueryFavorite(): Cursor {
        val cursor = favoriteDao.ContentProviderGetFavorites()
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {
            Log.d(
                "favContentProvider",
                "onLoadFinished : position ${cursor.position}"
            )

            val fav = mapCursorToFavorite(cursor)
            Log.d("favContentProvider", "onLoadFinished called $fav")
        }
        return cursor
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0


}
