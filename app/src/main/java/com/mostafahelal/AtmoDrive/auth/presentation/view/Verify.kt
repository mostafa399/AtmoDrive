package com.mostafahelal.AtmoDrive.auth.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.DataX
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.CheckCodeViewModel
import com.mostafahelal.AtmoDrive.databinding.FragmentVerifyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Verify : Fragment() {
    val viewModel: CheckCodeViewModel by viewModels()
    private lateinit var verifyBinding: FragmentVerifyBinding
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
        verifyBinding.continueVerifyButton.setOnClickListener {
            val otpCode=verifyBinding.pinView2.editableText.toString()
           val phoneNumber=arguments?.getString("PhoneNumber")
            if (otpCode.length==4){
                val result = CheckCodeRequest("mostafa:$phoneNumber",phoneNumber.toString(),otpCode)
                viewModel.checkCode(code = result)
                viewModel.successful.observe(viewLifecycleOwner){successful->

                    if (successful==true){
                        println("DATA SENT: $result")
                        findNavController().navigate(R.id.action_verify_NewUser_to_createAccount)
                        viewModel.navigationToPage()
                    }else if (successful==false){
                        findNavController().navigate(R.id.action_verify_NewUser_to_mapsFragment)
                        viewModel.navigationToPage()

                    }


                }

            }else{
                Snackbar.make(verifyBinding.continueVerifyButton,"Error", Snackbar.LENGTH_SHORT).show()

            }
        }

    }

}