package com.example.mynirvana.presentation.activities.onBoardingAcitivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.mynirvana.databinding.ActivityOnBoardingBinding
import com.example.mynirvana.presentation.activities.mainActivity.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!viewModel.isAppRanFirstTime) {
            startMainActivity()
        }


        binding = ActivityOnBoardingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initView()
    }

    private fun startMainActivity() {
        Intent(this, MainActivity::class.java).also {
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(it)
        }
    }

    private fun initMeditationCourses() = viewModel.createMeditationCourses()

    private fun initView() {

        binding.startUsingAppButton.isEnabled = false

        binding.userNameInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.startUsingAppButton.isEnabled = s.toString().isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.startUsingAppButton.setOnClickListener {
            saveUserNameToSharedPreferences(getUserNameFromEditText())
        }
    }

    private fun getUserNameFromEditText() = binding.userNameInputEditText.text.toString()

    private fun saveUserNameToSharedPreferences(userName: String) {
        viewModel.setUserName(userName) {
            initMeditationCourses()
            viewModel.setFalseToAppRanFirstTime()
            startMainActivity()
        }
    }
}