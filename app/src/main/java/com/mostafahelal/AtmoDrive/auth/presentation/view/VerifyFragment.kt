package com.mostafahelal.AtmoDrive.auth.presentation.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.Utils.NetworkState
import com.mostafahelal.AtmoDrive.Utils.showToast
import com.mostafahelal.AtmoDrive.Utils.visibilityGone
import com.mostafahelal.AtmoDrive.Utils.visibilityVisible
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.CheckCodeViewModel
import com.mostafahelal.AtmoDrive.databinding.FragmentVerifyBinding
import com.mostafahelal.AtmoDrive.maps.presenter.MapsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
@AndroidEntryPoint
class VerifyFragment : Fragment() {
    private val viewModel: CheckCodeViewModel by viewModels()
    private lateinit var verifyBinding: FragmentVerifyBinding
    private val args: VerifyFragmentArgs by navArgs()
    private var countdownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        verifyBinding = FragmentVerifyBinding.inflate(layoutInflater)
        return verifyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarNavigation()
        displayPhoneNumber()
        setupVerifyButton()
        initCountdownTimer(120000)
        verifyBinding.resendVerfyText.setOnClickListener {
            // Restart the countdown timer when the TextView is clicked
            restartCountdownTimer(120000) // 2 minutes in milliseconds
        }
    }

    private fun setupToolbarNavigation() {
        verifyBinding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun displayPhoneNumber() {
        val phoneNumber = args.mobilePhone
        val fullText = "Enter the OTP code sent at mobile number $phoneNumber to verify it's you"
        val subText = "$phoneNumber"
        val spannableStringBuilder = SpannableStringBuilder(fullText)
        val start = fullText.indexOf(subText)
        val end = start + subText.length
        val color = ContextCompat.getColor(requireContext(), R.color.primary)
        val foregroundColorSpan = ForegroundColorSpan(color)
        spannableStringBuilder.setSpan(foregroundColorSpan, start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)

        verifyBinding.textView2.text = spannableStringBuilder
    }

    private fun setupVerifyButton() {
        verifyBinding.continueVerifyButton.setOnClickListener {
            val otpCode = verifyBinding.pinView2.text.toString()
            val phoneNumber = args.mobilePhone
            val deviceToken = "device_token:$phoneNumber"
            if (otpCode.isNotEmpty()){
                viewModel.checkCode(deviceToken,phoneNumber,otpCode)
                observeNavigateToRegister()
                observeNavigateToMain()
            }
            else{
                showToast("The otp code is error ")
            }


        }
    }

    private fun observeNavigateToRegister() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToRegister.collect { networkState ->
                    when (networkState?.status) {
                        NetworkState.Status.SUCCESS -> {
                            withContext(Dispatchers.Main){
                                val action = VerifyFragmentDirections.actionVerifyNewUserToCreateAccount(phoneNumber = args.mobilePhone, deviceToken = "device_token ${args.mobilePhone}")
                                findNavController().navigate(action)
                                verifyBinding.pb2.visibilityGone()

                        }}
                        NetworkState.Status.FAILED->{
                            Log.d("VerifyFragment ", networkState.msg.toString())
                            verifyBinding.pb2.visibilityGone()


                        }
                        NetworkState.Status.RUNNING->{
                            verifyBinding.pb2.visibilityVisible()

                        }

                        else -> Unit
                    }
                }
            }
        }
    }
    private fun observeNavigateToMain() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToMain.collect { networkState ->
                    when (networkState?.status) {
                        NetworkState.Status.SUCCESS -> {
                            withContext(Dispatchers.Main){
                                startActivity(Intent(requireContext(), MapsActivity::class.java))
                                activity?.finish()
//                                val action = VerifyFragmentDirections.actionVerifyNewUserToMapsFragment()
//                                findNavController().navigate(action)
                                verifyBinding.pb2.visibilityGone()

                            }}
                        NetworkState.Status.FAILED->{
                            Log.d("VerifyFragment", networkState.msg.toString())
                            verifyBinding.pb2.visibilityGone()


                        }
                        NetworkState.Status.RUNNING->{
                            verifyBinding.pb2.visibilityVisible()


                        }
                        else -> Unit
                    }
                }
            }
        }
    }
    private fun initCountdownTimer(millisInFuture: Long) {
        countdownTimer = object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the TextView with the remaining time
                val secondsRemaining = millisUntilFinished / 1000
                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
                verifyBinding.resendVerfyText.text = "Resend ($formattedTime)"
            }

            override fun onFinish() {
                // Countdown timer finished, update the TextView
                verifyBinding.resendVerfyText.text = "Resend"
            }
        }
        countdownTimer?.start()
    }

    private fun restartCountdownTimer(millisInFuture: Long) {
        countdownTimer?.cancel() // Cancel the previous timer if running
        initCountdownTimer(millisInFuture)
        countdownTimer?.start()
    }

}
