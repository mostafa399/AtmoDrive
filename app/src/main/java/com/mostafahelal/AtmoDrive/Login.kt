package com.mostafahelal.AtmoDrive

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.findNavController
import java.lang.Exception
import java.lang.reflect.Type

class Login : Fragment() {
    val longText:String="By continue you agree to AtmoDrive’s Terms & Conditions and Privacy Policy"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView=view.findViewById<TextView>(R.id.termandprivacytext)


        val button = view.findViewById<Button>(R.id.continue_button)
        val navigationController = findNavController()

        button.setOnClickListener {
            navigationController.navigate(R.id.action_login_to_verify_NewUser)

        }



    }

}