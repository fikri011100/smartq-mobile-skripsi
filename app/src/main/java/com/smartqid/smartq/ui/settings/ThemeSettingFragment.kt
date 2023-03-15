package com.smartqid.smartq.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import com.smartqid.smartq.R

class ThemeSettingFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_theme_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
//            var rb = view.findViewById<RadioButton>(i)
//            if (rb != null) {
//                when (rb.text) {
//                    "Sesuai Perangkat" ->
//                        if (checked) {
//                        requireActivity().setTheme(R.style.fullScreen)
//                        }
//                    "Tema Terang" ->
//                        if (checked) {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//
//                        }
//                    "Tema Gelap" ->
//                        if (checked) {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//
//                        }
//                }
//            }
//        }

//        btn_switch.setOnClickListener {
//            val isNightTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
//            when (isNightTheme) {
//                Configuration.UI_MODE_NIGHT_YES ->
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                Configuration.UI_MODE_NIGHT_NO ->
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            }
//        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.System ->
                    if (checked) {
//                        requireActivity().setTheme(R.style.fullScreen)
                    }
                R.id.Terang ->
                    if (checked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                R.id.Gelap ->
                    if (checked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
            }
        }
    }


}