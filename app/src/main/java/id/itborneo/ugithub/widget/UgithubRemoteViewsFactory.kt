package id.itborneo.ugithub.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import id.itborneo.ugithub.R
import id.itborneo.ugithub.core.local.AppDatabase
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class UgithubRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val widgetImages = ArrayList<Bitmap>()
    private val widgetNames = ArrayList<String>()

    override fun onCreate() {}

    override fun onDataSetChanged() {
        val favorites = AppDatabase.getInstance(mContext).favoriteDao().getFavoritesWidget()
        favorites.forEach {
            getBitmapFromURL(it.avatarUrl)?.let { it1 ->
                widgetImages.add(
                    it1
                )
            }
            widgetNames.add(it.name)
        }
    }

    override fun getCount() = widgetImages.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.iv_image, widgetImages[position])
        rv.setTextViewText(R.id.tv_name, widgetNames[position])

        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    private fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            // Log exception
            null
        }
    }

    override fun onDestroy() {}

}