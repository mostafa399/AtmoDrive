package com.mostafahelal.AtmoDrive.auth.presentation.view
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.NetworkState
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.SendPhoneViewModel
import com.mostafahelal.AtmoDrive.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment() {
    val viewModel: SendPhoneViewModel by viewModels()
   // private val viewModelAuth: AuthViewModel by viewModels()
    private lateinit var loginBinding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        loginBinding=FragmentLoginBinding.inflate(layoutInflater)
        return loginBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginBinding=FragmentLoginBinding.bind(view)
        loginBinding.continueButton.setOnClickListener {
            val phoneNumber = loginBinding.phoneEt.editableText.toString()
            if (phoneNumber.length==10) {
                val phone = "0${phoneNumber}"
                viewModel.sendMobilePhone(phone)
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                        viewModel.sendCodeResult.collect{networkState->
                            when(networkState?.status){
                                 NetworkState.Status.SUCCESS->{
                                     val action=LoginFragmentDirections.actionLoginToVerifyNewUser(phone)
                                     findNavController().navigate(action)
                                     Log.d("LoginFragment",phone)
                                     // viewModel.navigationToPage()
                                 }
                                 NetworkState.Status.FAILED->{
                                     Log.d("LoginFragment", networkState.msg.toString())
                                     //viewModel.navigationToPage()
                                 }

                                else -> Unit

                            }
                            }

                        }
                }

                }
//                viewModel.successful.observe(viewLifecycleOwner){successful->
//                    if (successful==true){
//                        println("DATA SENT: $phone")
//                        val bundle = Bundle()
//                        bundle.putString("PhoneNumber", phone)
//                        findNavController().navigate(R.id.action_login_to_verify_NewUser)
//                        viewModel.navigationToPage()
//                    }else if (successful==false){
//                        Snackbar.make(loginBinding.continueButton,"${viewModel.error.value}", Snackbar.LENGTH_SHORT).show()
//                        viewModel.navigationToPage()
//
//                    }
//
//
//                }

//            }else{
//                Snackbar.make(loginBinding.continueButton,"Error", Snackbar.LENGTH_SHORT).show()
//
//            }
        }

    }
}


