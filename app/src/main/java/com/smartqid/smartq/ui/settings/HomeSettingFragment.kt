package com.smartqid.smartq.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.smartqid.smartq.R


class HomeSettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_setting, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        constraint_umum.setOnClickListener {
//            view.findNavController().navigate(R.id.action_homeSettingFragment_to_userSettingName)
//        }
//        constraint_tema.setOnClickListener {
//            view.findNavController().navigate(R.id.action_homeSettingFragment_to_themeSettingFragment)
//        }
//        constraint_kritik.setOnClickListener {
//            Toast.makeText(context,"Comming Soon", Toast.LENGTH_LONG).show()
//        }
//        img_back_home_setting.setOnClickListener {
//            requireActivity().onBackPressed()
//        }
    }

}