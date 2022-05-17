package com.example.mynirvana.presentation.activities.onBoardingAcitivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mynirvana.R
import com.example.mynirvana.databinding.ActivityOnBoardingBinding
import com.example.mynirvana.presentation.activities.mainActivity.OnBoardingActivityCallback
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    companion object {
        private lateinit var callback: OnBoardingActivityCallback
    }

    fun provideCallback(callback: OnBoardingActivityCallback) {
        OnBoardingActivity.callback = callback
    }

    private lateinit var binding: ActivityOnBoardingBinding
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()
    }

    private fun initView() {

        binding.startUsingAppButton.isEnabled = false

        binding.userNameInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

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
            callback.onBoardingActivityOnBackPressed()
            onBackPressed()
        }
    }
}