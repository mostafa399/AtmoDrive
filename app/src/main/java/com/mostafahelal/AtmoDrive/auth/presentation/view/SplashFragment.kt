package com.mostafahelal.AtmoDrive.auth.presentation.view

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
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    @Inject
    lateinit var  viewModel: SplashViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                // Check if the user is logged in
                if (viewModel.loggedIn) {
                    // User is logged in, navigate directly to MapsFragment
                    findNavController().navigate(R.id.action_splashFragment_to_mapsFragment)
                } else {
                    // User is not logged in, show the splash screen for 3 seconds
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigate(R.id.action_splashFragment_to_intro)
                    }, 3000L)
                }
            }
        }

    }
}
