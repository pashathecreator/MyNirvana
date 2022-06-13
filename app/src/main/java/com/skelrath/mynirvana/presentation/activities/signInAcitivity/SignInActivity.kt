package com.skelrath.mynirvana.presentation.activities.signInAcitivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.skelrath.mynirvana.databinding.ActivitySignInBinding
import com.skelrath.mynirvana.databinding.ActivitySignUpBinding
import com.skelrath.mynirvana.presentation.activities.signUpActivity.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        initView()
        setContentView(binding.root)
    }

    private fun initView() {
        with(binding) {
            signInButton.setOnClickListener {
                val email = emailLoginInputEditText.text.toString()
                val password = passwordLoginInputEditText.text.toString()
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

                }
            }
        }
    }
}