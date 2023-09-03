package com.mostafahelal.AtmoDrive.auth.presentation.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.NetworkState
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.domain.model.NewPassengerRequest
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.RegisterPassengerViewModel
import com.mostafahelal.AtmoDrive.databinding.FragmentCreateAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CreateAccountFragment : Fragment() {
    private lateinit var registerBinding:FragmentCreateAccountBinding
    val viewModel:RegisterPassengerViewModel by viewModels()
    private val args :CreateAccountFragmentArgs by navArgs()
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
          findNavController().navigate(R.id.action_createAccount_to_login)

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {
        registerBinding=FragmentCreateAccountBinding.inflate(layoutInflater)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
        setupToolbarNavigation()
        return registerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBinding=FragmentCreateAccountBinding.bind(view)
        val email=  registerBinding.editTextTextEmailAddress.editableText.toString()
        val phoneNumber=args.phoneNumber
        val device_token=args.deviceToken
        registerBinding.loginButtonCreate.setOnClickListener {
            val fullName=  registerBinding.editTextText.text.toString()
            if (fullName.length in 3..25){
                 val request=RegisterPassengerRequest(fullName = fullName, deviceToken = device_token,
                     mobile = phoneNumber, avatar = "null", deviceId = "null", deviceType = "android", email = email)
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.registerPassenger(request)
                    viewModel.registerState.collect { networkState->
                        when(networkState?.status){
                            NetworkState.Status.SUCCESS->{
                                withContext(Dispatchers.Main){
                                val action=CreateAccountFragmentDirections.actionCreateAccountToMapsFragment()
                                findNavController().navigate(action)
                            }
                            }
                            else -> Unit
                        }

                    }

                }
            }

        }
        }
    }
    private fun setupToolbarNavigation() {
        registerBinding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_createAccount_to_login)
        }
    }

}