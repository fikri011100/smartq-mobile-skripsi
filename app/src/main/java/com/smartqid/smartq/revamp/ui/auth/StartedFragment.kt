package com.smartqid.smartq.revamp.ui.auth

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.FragmentStartedBinding
import com.smartqid.smartq.revamp.common.BaseFragment
import com.smartqid.smartq.revamp.ui.home.HomeActivity
import timber.log.Timber


class StartedFragment : BaseFragment<FragmentStartedBinding>() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient //google
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStartedBinding
        get() = FragmentStartedBinding::inflate

    override fun initObservable() {

    }

    override fun initView() {
        mAuth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        binding.btnGoogle.setOnClickListener {
            signIn()
        }
        binding.btnFacebook.setOnClickListener {
            Toast.makeText(context,"Coming Soon", Toast.LENGTH_LONG).show()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
//                Log.d("SignIn", "firebaseAuthWithGoogle:" + account.id)
                Timber.d("firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Timber.w("Google sign in failed $e")
//                Log.w("SignIn", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val database = FirebaseDatabase.getInstance().getReference("users")
                    val query: Query = database.orderByChild("email").equalTo(mAuth.currentUser.email).apply {
                        addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.value == null) {
                                    saveToDatabase(mAuth.currentUser.displayName,mAuth.currentUser.email)
                                }
                                Log.d("SignInnnn", snapshot.value.toString())
                                navigateToMain()
                            }
                            override fun onCancelled(error: DatabaseError) {
                                Log.d("SignIn", "error signin")
                                navigateToMain()
                            }
                        })
                    }
                } else {
                    Log.w("SignIn", "signInWithCredential:failure", task.exception)
                    requireActivity().finish()
                }
            }
    }

    private fun saveToDatabase(name: String, email: String) {
        //mendapatkan id dari user saat register
        val uid = mAuth.uid
        //menginisiasi instance database
        val database = Firebase.database
        //menambahkan referensi
        val myRef = database.getReference("users/$uid")

        val userModel = HashMap<String, String>()
        userModel["name"] = name
        userModel["email"] = email
        userModel["telephone"] = ""
        userModel["address"] = ""
        userModel["gender"] = ""

        myRef.setValue(userModel)

    }
    private fun navigateToMain(){
        val intent = Intent(activity, HomeActivity::class.java)
        requireContext().startActivity(intent)
        requireActivity().finish()
    }
    companion object{
        private const val GOOGLE_SIGN_IN = 1903
    }
}