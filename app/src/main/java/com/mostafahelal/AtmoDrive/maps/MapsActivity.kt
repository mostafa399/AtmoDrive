package com.mostafahelal.AtmoDrive.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mostafahelal.AtmoDrive.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity() {

    private var _binding: ActivityMapsBinding?=null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}