package com.mostafahelal.AtmoDrive.auth.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.NetworkState
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.CheckCodeViewModel
import com.mostafahelal.AtmoDrive.databinding.FragmentVerifyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerifyFragment : Fragment() {
    val viewModel: CheckCodeViewModel by viewModels()
    private lateinit var verifyBinding: FragmentVerifyBinding
    private val args :VerifyFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        verifyBinding=FragmentVerifyBinding.inflate(layoutInflater)
        return verifyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verifyBinding=FragmentVerifyBinding.bind(view)
        val phoneNumber=args.mobilePhone


        verifyBinding.continueVerifyButton.setOnClickListener {

            val otpCode=verifyBinding.pinView2.editableText.toString()
                val result = CheckCodeRequest(verificationCode = otpCode, mobile = phoneNumber, deviceToken = "device_token:$phoneNumber")
                viewModel.checkCode(result)
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.navigateToMain.collect{networkState->
                        when(networkState?.status){
                            NetworkState.Status.SUCCESS->{
                                val action=VerifyFragmentDirections.actionVerifyNewUserToMapsFragment()
                                findNavController().navigate(action)
                            }

                            else -> Unit
                        }

                    }

                }
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.navigateToRegister.collect{networkState->
                        when(networkState?.status){
                            NetworkState.Status.SUCCESS->{
                                val action=VerifyFragmentDirections.actionVerifyNewUserToCreateAccount()
                                findNavController().navigate(action)
                            }

                            else -> Unit
                        }

                    }
            }
//                viewModel.navigateToCreatePassenger.observe(viewLifecycleOwner){navigate->
//                    if (navigate){
//                        findNavController().navigate(R.id.action_verify_NewUser_to_createAccount)
//                        viewModel.onNavigateToCreatePassengerComplete()
//                        println("DATA SENT $result")
//                    }
//
//                }
//                viewModel.navigateToRegisterPassenger.observe(viewLifecycleOwner){navigate->
//                    if (navigate){
//                        findNavController().navigate(R.id.action_verify_NewUser_to_mapsFragment)
//                        viewModel.onNavigateToRegisterPassengerComplete()
//                        println("DATA SENT $result")
//
//                    }
//
//                }
//                }else{
//                Snackbar.make(verifyBinding.continueVerifyButton,"Error", Snackbar.LENGTH_SHORT).show()
//
//            }
//
//            }

        }
}
}
}