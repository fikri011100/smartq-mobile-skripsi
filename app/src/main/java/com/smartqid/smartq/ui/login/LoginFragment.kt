package com.smartqid.smartq.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.FragmentLoginBinding
import com.smartqid.smartq.revamp.ui.home.HomeActivity

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
//        view.back.setOnClickListener {
//            findNavController().navigate(R.id.action_loginFragment_to_startedFragment)
//        }
//        view.button.setOnClickListener{
//
//            Login()
//        }
    }
    fun Login(){
      activity?.let {
          val intent = Intent (it, HomeActivity::class.java)
          it.startActivity(intent)
      }
    }

}