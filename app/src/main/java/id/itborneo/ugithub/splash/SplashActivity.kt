package id.itborneo.ugithub.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import id.itborneo.ugithub.databinding.ActivitySplashBinding
import id.itborneo.ugithub.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        timerToNavigation()
    }

    private fun initBinding() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun timerToNavigation() {
        lifecycleScope.launch {
            delay(2000L)
            binding.spinKitLoading.visibility = View.GONE
            actionToMainActivity()
        }
    }

    private fun actionToMainActivity() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}