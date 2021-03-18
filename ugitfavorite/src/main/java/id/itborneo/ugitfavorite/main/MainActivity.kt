package id.itborneo.ugitfavorite.main

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import id.itborneo.ugitfavorite.core.utils.ContentProvider.mapCursorToFavorite
import id.itborneo.ugitfavorite.core.model.FavoriteModel
import id.itborneo.ugitfavorite.R
import id.itborneo.ugitfavorite.core.model.UserModel
import id.itborneo.ugitfavorite.databinding.ActivityMainBinding
import id.itborneo.ugitfavorite.detail.DetailActivity
import id.itborneo.ugitfavorite.core.utils.DataMapperModel

class MainActivity : AppCompatActivity() {

    companion object {
        const val AUTHORITY = "id.itborneo.ugithub"
        const val SCHEME = "content"
        const val TABLE_NAME = "db_favorite"
        const val FAVORITE = 1
    }

    private lateinit var adapter: MainAdapter
    private lateinit var binding: ActivityMainBinding
//    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBinding()
        initRecycler()
//        initNav(view)
        observerFavorite()
        initContentProvider()
    }

    private val viewModel: MainViewModel by viewModels()

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

    private fun observerFavorite() {
//        viewModel.favorites.observe(this) {
//            adapter.set(it)
//            if (it.isEmpty()) {
//                emptyListUI(true)
//            } else {
//                emptyListUI(false)
//            }
//        }
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

    val URI: Uri = Uri.parse("$SCHEME://$AUTHORITY/$TABLE_NAME")

    lateinit var dataCursor: Cursor

    private val mLoaderCallbacks = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            return when (id) {
                FAVORITE -> CursorLoader(applicationContext, URI, null, null, null, null)
                else -> throw IllegalArgumentException("Main Activity: Unknown URI")
            }
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            when (loader.id) {
                FAVORITE -> dataCursor = data as Cursor

            }

            dataCursor.moveToFirst()
            val favoriteUsers = mutableListOf<FavoriteModel>()
            while (!dataCursor.isAfterLast) {
                Log.d("MainActivity", "onLoadFinished : position ${dataCursor.position}")

                val fav = mapCursorToFavorite(dataCursor)
                Log.d("MainActivity", "onLoadFinished called $fav")
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
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}