package com.mostafahelal.AtmoDrive.auth.presentation.view

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mostafahelal.AtmoDrive.auth.data.data_source.local.ISharedPrefrenceManager
import com.mostafahelal.AtmoDrive.auth.data.data_source.local.SharedPrefernceManager
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.CheckCodeViewModel
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.RegisterPassengerViewModel
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.SplashViewModel
import com.mostafahelal.AtmoDrive.databinding.FragmentMapsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class MapsFragment : Fragment() {

    private val viewModel: CheckCodeViewModel by viewModels()
    private val regiterviewModel: RegisterPassengerViewModel by viewModels()
    private lateinit var mapsBinding: FragmentMapsBinding
    private var backPressedCounter = 0
    private val doubleBackPressInterval = 2000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapsBinding = FragmentMapsBinding.inflate(inflater, container, false)
        return mapsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupBackPressedHandler()
    }

    private fun setupUI() {
        mapsBinding.Logout.setOnClickListener {
            viewModel.deleteAccessToken()
            val action = MapsFragmentDirections.actionMapsFragmentToLogin()
            findNavController().navigate(action)
        }
    }

    private fun setupBackPressedHandler() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedCounter == 1) {
                    // User pressed back button twice within the interval, exit the app

                        requireActivity().finish()


                } else {
                    // User pressed back button once, show a toast
                    Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show()
                    backPressedCounter++

                    // Reset the counter after the interval
                    Handler(Looper.getMainLooper()).postDelayed({
                        backPressedCounter = 0
                    }, doubleBackPressInterval.toLong())
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
