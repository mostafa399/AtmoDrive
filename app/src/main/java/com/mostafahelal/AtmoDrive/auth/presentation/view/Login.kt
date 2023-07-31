package com.mostafahelal.AtmoDrive.auth.presentation.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.SendCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.SendPhoneViewModel
import com.mostafahelal.AtmoDrive.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Login : Fragment() {
    val viewModel: SendPhoneViewModel by viewModels()
    private lateinit var loginBinding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        loginBinding=FragmentLoginBinding.inflate(layoutInflater)
        return loginBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginBinding=FragmentLoginBinding.bind(view)
        loginBinding.continueButton.setOnClickListener{
            val phoneNumber=loginBinding.phoneEt.editableText.toString()
            if (phoneNumber.length==10) {
                val phone="0${phoneNumber}"
                val result = SendCodeRequest(phone)
                viewModel.sendMobilePhone(phone = result)
                viewModel.successful.observe(viewLifecycleOwner){successful->
                    if (successful==true){
                        println("DATA SENT: $result")
                        val bundle = Bundle()
                        bundle.putString("PhoneNumber", result.toString())
                        findNavController().navigate(R.id.action_login_to_verify_NewUser)
                        viewModel.navigationToPage()
                    }else if (successful==false){
                        Snackbar.make(loginBinding.continueButton,"${viewModel.error.value}", Snackbar.LENGTH_SHORT).show()
                        viewModel.navigationToPage()

                    }


                }

            }else{
                Snackbar.make(loginBinding.continueButton,"Error", Snackbar.LENGTH_SHORT).show()

            }
        }

    }

}