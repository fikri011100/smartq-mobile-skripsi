package com.smartqid.smartq.revamp.ui.auth

import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.R
import com.smartqid.smartq.common.SharedPref
import com.smartqid.smartq.databinding.FragmentSplashBinding
import com.smartqid.smartq.revamp.common.BaseFragment
import com.smartqid.smartq.revamp.ui.home.HomeActivity


class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private lateinit var auth: FirebaseAuth
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate

    override fun initObservable() {

    }

    override fun initView() {
        Handler().postDelayed({
            auth = Firebase.auth
            val currentUser = auth.currentUser
            if (SharedPref(requireContext()).isFirstTime() == 0) {
                SharedPref(requireContext()).firstTime()
                findNavController().navigate(R.id.action_splashFragment_to_introFragment)
            } else {
                if (currentUser != null) {
                    navigateToMain()
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_startedFragment)
                }
            }
        },5000)
    }

    private fun navigateToMain(){
        val intent = Intent(activity, HomeActivity::class.java)
        requireContext().startActivity(intent)
        requireActivity().finish()
    }

}