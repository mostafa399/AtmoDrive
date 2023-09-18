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
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUiSaveStateControl
import com.hbb20.CountryCodePicker
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.NetworkState
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.visibilityGone
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.visibilityVisible
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.SendPhoneViewModel
import com.mostafahelal.AtmoDrive.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel: SendPhoneViewModel by viewModels()
    private lateinit var loginBinding: FragmentLoginBinding

    private var backPressedCounter = 0
    private val doubleBackPressInterval = 2000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        loginBinding = FragmentLoginBinding.inflate(layoutInflater)
        loginBinding.ccp.setCountryForPhoneCode(20) // Use 20 instead of +20
        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginBinding = FragmentLoginBinding.bind(view)

        setupContinueButton()
        setupBackPressedHandler()
    }

    override fun onResume() {
        super.onResume()
        // Reset the backPressedCounter when the fragment resumes
        backPressedCounter = 0
    }

    private fun setupContinueButton() {
        loginBinding.continueButton.setOnClickListener {
            val phoneNumber = loginBinding.phone.editableText.toString()
            if (phoneNumber.length == 10) {
                val validPrefixes = listOf("11","12", "15", "10")
                if (validPrefixes.any { phoneNumber.startsWith(it) }) {
                    val phone = "0$phoneNumber"
                    viewModel.sendMobilePhone(phone)
                    observeSendCodeResult()
                } else {
                    Toast.makeText(requireContext(), "Phone number does not exist", Toast.LENGTH_SHORT).show()
                }
            }
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
    private fun observeSendCodeResult() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.sendCodeResult.collect { networkState ->
                    when (networkState?.status) {
                        NetworkState.Status.SUCCESS -> {
                            val phone = loginBinding.phone.editableText.toString()
                            withContext(Dispatchers.Main){
                            val action = LoginFragmentDirections.actionLoginFragmentToVerifyFragment("0$phone")
                            findNavController().navigate(action)
                                  loginBinding.pb.visibilityGone()
                        }
                        }
                        NetworkState.Status.FAILED -> {
                            Log.d("LoginFragment", networkState.msg.toString())
                            loginBinding.pb.visibilityGone()

                        }
                        NetworkState.Status.RUNNING -> {
                            loginBinding.pb.visibilityVisible()

                        }

                        else -> Unit
                    }
                }
            }
        }

    }

}
