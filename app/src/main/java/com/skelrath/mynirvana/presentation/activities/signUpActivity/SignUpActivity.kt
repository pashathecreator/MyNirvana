package com.skelrath.mynirvana.presentation.activities.signUpActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.skelrath.mynirvana.databinding.ActivitySignUpBinding
import com.skelrath.mynirvana.presentation.activities.mainActivity.MainActivity
import com.skelrath.mynirvana.presentation.activities.signInAcitivity.SignInActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private val viewModel: SignUpViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        checkUser()
        initView()
        setContentView(binding.root)
    }

    private fun checkUser() {
        firebaseAuth.currentUser?.let {
            startMainActivity()
        }
    }

    private fun initView() {
        initIsRegisterButtonEnabledObserveOnEditTexts()

        with(binding) {
            registerButton.isEnabled = false

            registerButton.setOnClickListener {
                val userName = userNameInputEditText.text.toString()
                val email = emailInputEditText.text.toString()
                val password = passwordInputEditText.text.toString()

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        saveUserNameToSharedPreferences(userName)
                        addUserToRealTimeBase(firebaseAuth.currentUser!!, userName)
                        initMeditationCourses()
                        startMainActivity()
                    } else {
                        Log.e("firebase", it.exception.toString())
                    }
                }
            }

            startSignInActivityButton.setOnClickListener {
                startSignInActivity()
            }
        }
    }

    private fun addUserToRealTimeBase(firebaseUser: FirebaseUser, userName: String) {
        val databaseReference = firebaseDatabase.reference
        databaseReference.child("users").child(firebaseUser.uid).child("user_name")
            .setValue(userName)
    }

    private fun saveUserNameToSharedPreferences(userName: String) = viewModel.setUserName(userName)

    private fun initIsRegisterButtonEnabledObserveOnEditTexts() {
        var isUserNameNotEmpty = false
        var isEmailNotEmpty = false
        var isPasswordNotEmpty = false
        var isConfirmedPasswordNotEmpty = false

        with(binding) {
            userNameInputEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    println()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    isUserNameNotEmpty = s.toString().isNotEmpty()

                    registerButton.isEnabled =
                        isUserNameNotEmpty && isEmailNotEmpty && isPasswordNotEmpty && isConfirmedPasswordNotEmpty
                                && passwordInputEditText.text.toString() == confirmPasswordInputEditText.text.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                    println()
                }
            })

            emailInputEditText.addTextChangedListener(object : TextWatcher {
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
                    registerButton.isEnabled =
                        isUserNameNotEmpty && isEmailNotEmpty && isPasswordNotEmpty && isConfirmedPasswordNotEmpty
                                && passwordInputEditText.text.toString() == confirmPasswordInputEditText.text.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                    println()
                }
            })

            passwordInputEditText.addTextChangedListener(object : TextWatcher {
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

                    registerButton.isEnabled =
                        isUserNameNotEmpty && isEmailNotEmpty && isPasswordNotEmpty && isConfirmedPasswordNotEmpty
                                && passwordInputEditText.text.toString() == confirmPasswordInputEditText.text.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                    println()
                }
            })

            confirmPasswordInputEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    println()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    isConfirmedPasswordNotEmpty = s.toString().isNotEmpty()

                    registerButton.isEnabled =
                        isUserNameNotEmpty && isEmailNotEmpty && isPasswordNotEmpty && isConfirmedPasswordNotEmpty
                                && passwordInputEditText.text.toString() == confirmPasswordInputEditText.text.toString()
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

    private fun startSignInActivity() {
        Intent(this, SignInActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun initMeditationCourses() = viewModel.createMeditationCourses()
}