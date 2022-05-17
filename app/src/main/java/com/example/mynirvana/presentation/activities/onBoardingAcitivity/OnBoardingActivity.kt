package com.example.mynirvana.presentation.activities.onBoardingAcitivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.mynirvana.databinding.ActivityMainBinding
import com.example.mynirvana.databinding.ActivityOnBoardingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.userNameInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.startUsingAppButton.isActivated = count > 0
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.startUsingAppButton.setOnClickListener {
            saveUserNameToSharedPreferences(getUserNameFromEditText())

            onBackPressed()
        }
    }

    private fun getUserNameFromEditText() = binding.userNameInputEditText.text.toString()

    private fun saveUserNameToSharedPreferences(userName: String) {
        viewModel.setUserName(userName)
    }
}