package id.itborneo.ugithub

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.liveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.Resource
import id.itborneo.ugithub.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initBottomNav()


    }



    private fun initBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

    }
}