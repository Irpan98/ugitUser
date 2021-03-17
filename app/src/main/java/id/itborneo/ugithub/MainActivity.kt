package id.itborneo.ugithub

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import id.itborneo.ugithub.core.local.AppDatabase.Companion.TABLE_NAME
import id.itborneo.ugithub.core.local.FavoriteContentProvider.Companion.AUTHORITY
import id.itborneo.ugithub.core.local.FavoriteContentProvider.Companion.FAVORITE
import id.itborneo.ugithub.core.local.FavoriteContentProvider.Companion.SCHEME
import id.itborneo.ugithub.core.utils.ContentProvider.mapCursorToFavorite
import id.itborneo.ugithub.core.utils.KsPref
import id.itborneo.ugithub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSharedPreferences()
        initBinding()
        initBottomNav()
        initContentProvider()
    }

    private fun initContentProvider() {
        supportLoaderManager.initLoader(FAVORITE, null, mLoaderCallbacks)
    }

    private fun initSharedPreferences() {
        KsPref.instance(this)
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun initBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
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
            while (!dataCursor.isAfterLast) {
                Log.d("MainActivity", "onLoadFinished : position ${dataCursor.position}")

                val fav = mapCursorToFavorite(dataCursor)
                Log.d("MainActivity", "onLoadFinished called $fav")
            }


//            while (dataCursor.moveToNext()){


//            }


        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}