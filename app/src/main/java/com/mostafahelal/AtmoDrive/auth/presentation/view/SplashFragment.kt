package com.mostafahelal.AtmoDrive.auth.presentation.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
   @Inject
   lateinit var  viewModel: SplashViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({

//            if (viewModel.loggedIn){
//                findNavController().navigate(R.id.action_splashFragment_to_mapsFragment)
//            }
//            else{
//           findNavController().navigate(R.id.action_splashFragment_to_intro)
//            }
            findNavController().navigate(R.id.action_splashFragment_to_intro)
        },3000L)
    }
}