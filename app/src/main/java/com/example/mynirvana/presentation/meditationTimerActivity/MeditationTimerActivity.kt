package com.example.mynirvana.presentation.meditationTimerActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import com.example.mynirvana.databinding.ActivityMeditationTimerBinding
import java.util.*

class MeditationTimerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeditationTimerBinding
    private val viewModel: MeditationTimerViewModel by viewModels()
    private var secondsRemainingInString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()

        val totalTimeInSeconds: Long = intent.getSerializableExtra("TIME IN SECONDS") as Long
        viewModel.startTimer(totalTimeInSeconds)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.actionButton.setOnClickListener {
        }

    }

    private fun initObserver() {
        viewModel.remainingTime.observe(this) {
            this.secondsRemainingInString = secondsInLongToStringFormat(it)
        }
    }

    private fun secondsInLongToStringFormat(seconds: Long): String {
        var tempSeconds = seconds
        val minutes = seconds / 60
        tempSeconds -= 60 * minutes

        return "$minutes:$tempSeconds}"
    }

}


