package id.itborneo.ugithub.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import id.itborneo.ugithub.R
import id.itborneo.ugithub.core.local.AppDatabase
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class UgithubRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()

    override fun onCreate() {}

    override fun onDataSetChanged() {

        val favorites = AppDatabase.getInstance(mContext).favoriteDao().getFavoritesWidget()
        favorites.forEach {
            getBitmapFromURL(it.avatarUrl)?.let { it1 ->
                mWidgetItems.add(
                    it1
                )
            }
        }
    }

    override fun onDestroy() {
    }

    override fun getCount() = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
//        Log.d("UgithubRemoteViewsFac", "getViewAt : called")

        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])
        val extras = bundleOf(
            UgithubWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
//        Log.d("UgithubRemoteViewsFac", "getViewAt : return")

        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        Log.d("UgithubRemoteViewsFac", "getLoadingView : called")
        return null
    }

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false


    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            // Log exception
            null
        }
    }
}