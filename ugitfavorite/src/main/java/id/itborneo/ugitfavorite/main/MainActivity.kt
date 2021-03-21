package id.itborneo.ugitfavorite.main

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import id.itborneo.ugitfavorite.R
import id.itborneo.ugitfavorite.core.model.FavoriteModel
import id.itborneo.ugitfavorite.core.model.UserModel
import id.itborneo.ugitfavorite.core.utils.ContentProvider.mapCursorToFavorite
import id.itborneo.ugitfavorite.core.utils.DataMapperModel
import id.itborneo.ugitfavorite.core.utils.DatabaseConstant.AUTHORITY
import id.itborneo.ugitfavorite.core.utils.DatabaseConstant.FAVORITE
import id.itborneo.ugitfavorite.core.utils.DatabaseConstant.SCHEME
import id.itborneo.ugitfavorite.core.utils.DatabaseConstant.TABLE_NAME
import id.itborneo.ugitfavorite.databinding.ActivityMainBinding
import id.itborneo.ugitfavorite.detail.DetailActivity

class MainActivity : AppCompatActivity() {


    private lateinit var adapter: MainAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initRecycler()
        initContentProvider()
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun initContentProvider() {
        LoaderManager.getInstance(this).initLoader(FAVORITE, null, mLoaderCallbacks)
    }

    private fun initRecycler() {
        adapter = MainAdapter {
            actionToDetail(DataMapperModel.singleFavoriteToUser(it))
        }

        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context, 2)
        }
        binding.rvFavorites.adapter = this.adapter
    }

    private fun actionToDetail(user: UserModel) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(intent)
    }

    private fun emptyListUI(isEmpty: Boolean) {
        if (isEmpty) {
            binding.incEmpty.apply {
                root.visibility = View.VISIBLE
                tvTitle.text = getString(R.string.empty_list_favorite)
            }
        }
    }

    private val mLoaderCallbacks = object : LoaderManager.LoaderCallbacks<Cursor> {
        lateinit var dataCursor: Cursor

        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            val uri: Uri = Uri.parse("$SCHEME://$AUTHORITY/$TABLE_NAME")

            return when (id) {
                FAVORITE -> CursorLoader(applicationContext, uri, null, null, null, null)
                else -> throw IllegalArgumentException("Main Activity: Unknown URI")
            }
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            when (loader.id) {
                FAVORITE -> dataCursor = data as Cursor
                else -> throw IllegalArgumentException("Main Activity: Unknown loader id")
            }
            dataCursor.moveToFirst()
            val favoriteUsers = mutableListOf<FavoriteModel>()
            while (!dataCursor.isAfterLast) {
                val fav = mapCursorToFavorite(dataCursor)
                if (fav != null) {
                    favoriteUsers.add(fav)
                }
            }

            if (!favoriteUsers.isNullOrEmpty()) {
                adapter.set(favoriteUsers)
                emptyListUI(false)
            } else {
                emptyListUI(true)
            }
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
        }
    }
}