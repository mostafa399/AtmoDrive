package com.mostafahelal.AtmoDrive.auth.presentation.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.CheckCodeViewModel
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.SplashViewModel
import com.mostafahelal.AtmoDrive.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    @Inject
    lateinit var viewModel: SplashViewModel
    private lateinit var splashBinding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        splashBinding = FragmentSplashBinding.inflate(layoutInflater)
        return splashBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            if (viewModel.loggedIn) {
                withContext(Dispatchers.Main) {
                    // User is already logged in, navigate to MapsFragment

                    val action = SplashFragmentDirections.actionSplashFragmentToMapsFragment()
                    findNavController().navigate(action)
                }
            } else {
                withContext(Dispatchers.Main) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        // User is not logged in, navigate to Intro
                        val action = SplashFragmentDirections.actionSplashFragmentToIntro()
                        findNavController().navigate(action)
                    }, 3000L)
                }
            }
        }
    }
}

