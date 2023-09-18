package com.mostafahelal.AtmoDrive.auth.presentation.view

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.CheckCodeViewModel
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.SplashViewModel
import com.mostafahelal.AtmoDrive.databinding.FragmentIntroBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class IntroFragment : Fragment() {
    private lateinit var introBinding:FragmentIntroBinding
    private val callback=object :OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view=  inflater.inflate(R.layout.fragment_intro, container, false)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        introBinding=FragmentIntroBinding.bind(view)
        observetoLogin()
        applySpannableText()
    }
    private fun observetoLogin(){
        lifecycleScope.launch(Dispatchers.Main) {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                introBinding.constraintLayout7.setOnClickListener {
                    val action=IntroFragmentDirections.actionIntroToLogin()
                    findNavController().navigate(action)
                }
            }
        }
    }
    fun applySpannableText() {
        val fullText = "Looking for a ride? Try AtmoDrive and Book now!"
        val subText = "AtmoDrive"
        val spannableStringBuilder = SpannableStringBuilder(fullText)
        val start = fullText.indexOf(subText)
        val end = start + subText.length
        val color = ContextCompat.getColor(requireContext(), R.color.primary)
        val foregroundColorSpan = ForegroundColorSpan(color)
        spannableStringBuilder.setSpan(foregroundColorSpan, start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)
        introBinding.introtext.text = spannableStringBuilder
    }


}