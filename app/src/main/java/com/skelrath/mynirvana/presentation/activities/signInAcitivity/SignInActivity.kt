package com.skelrath.mynirvana.presentation.activities.signInAcitivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.skelrath.mynirvana.databinding.ActivitySignInBinding
import com.skelrath.mynirvana.presentation.activities.mainActivity.MainActivity
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
        initIsRegisterButtonEnabledObserveOnEditTexts()

        with(binding) {
            signInButton.isEnabled = false

            signInButton.setOnClickListener {
                val email = emailLoginInputEditText.text.toString()
                val password = passwordLoginInputEditText.text.toString()
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {

                        downloadDataOfUserFromRealtimeDatabase()
                        startMainActivity()
                    } else {
                        Toast.makeText(
                            this@SignInActivity,
                            "Неправильно введенный email или пароль!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun initIsRegisterButtonEnabledObserveOnEditTexts() {
        var isEmailNotEmpty = false
        var isPasswordNotEmpty = false
        with(binding) {
            emailLoginInputEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    println()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    isEmailNotEmpty = s.toString().isNotEmpty() && s.toString().contains('@')
                    signInButton.isEnabled = isEmailNotEmpty && isPasswordNotEmpty
                }

                override fun afterTextChanged(s: Editable?) {
                    println()
                }
            })

            passwordLoginInputEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    println()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    isPasswordNotEmpty = s.toString().isNotEmpty()
                    signInButton.isEnabled = isEmailNotEmpty && isPasswordNotEmpty
                }

                override fun afterTextChanged(s: Editable?) {
                    println()
                }
            })
        }
    }

    private fun startMainActivity() {
        Intent(this, MainActivity::class.java).also {
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(it)
        }
    }

    private fun downloadDataOfUserFromRealtimeDatabase() {
        viewModel.getUserNameFromRealTimeDatabase(firebaseAuth.currentUser!!, firebaseDatabase)
        viewModel.downloadDataOfUserFromRealtimeDatabase(
            firebaseAuth.currentUser!!,
            firebaseDatabase
        )
    }
}
