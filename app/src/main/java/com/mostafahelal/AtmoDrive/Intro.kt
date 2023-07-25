package com.mostafahelal.AtmoDrive

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class Intro : Fragment() {
    private lateinit var spannableColorChanged: SpannableColorChanged
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view=  inflater.inflate(R.layout.fragment_intro, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigationController=findNavController()
        val imageForward=view.findViewById<ImageView>(R.id.imageforward)
        imageForward.setOnClickListener{
            navigationController.navigate(R.id.action_intro_to_login)
        }

        spannableColorChanged=SpannableColorChanged(view)
        spannableColorChanged.applySpannableText()
    }
    class SpannableColorChanged(private val view: View) {
        private val textView: TextView = view.findViewById(R.id.introtext)
        fun applySpannableText() {
            val fullText = "Looking for a ride? Try AtmoDrive and Book now!"
            val subText = "AtmoDrive"
            val spannableStringBuilder = SpannableStringBuilder(fullText)
            val start = fullText.indexOf(subText)
            val end = start + subText.length
            val color = ContextCompat.getColor(view.context, R.color.primary)
            val foregroundColorSpan = ForegroundColorSpan(color)
            spannableStringBuilder.setSpan(foregroundColorSpan, start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)
            textView.text = spannableStringBuilder
        } }

}