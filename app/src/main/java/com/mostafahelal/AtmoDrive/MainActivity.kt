package com.mostafahelal.AtmoDrive
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.view.WindowCompat
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.auth.presentation.view.IntroFragment
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WindowCompat.setDecorFitsSystemWindows(window, false)




    }



}