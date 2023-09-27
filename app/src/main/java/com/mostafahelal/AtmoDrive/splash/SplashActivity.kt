package com.mostafahelal.AtmoDrive.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mostafahelal.AtmoDrive.MainActivity
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.SplashViewModel
import com.mostafahelal.AtmoDrive.maps.MapsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                if (viewModel.loggedIn) {
                   startActivity(Intent(applicationContext, MapsActivity::class.java))
                    finish()

                }
                else {
                        delay(3000L)
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                }
    }
    }
}
}