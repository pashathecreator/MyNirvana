package com.example.mynirvana.presentation.mainFragments.productivityFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynirvana.databinding.FragmentProductivityBinding
import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.domain.pomodoro.readyPomodorosData.ReadyPomodoros
import com.example.mynirvana.presentation.activities.pomodoros.pomodoroTimerActivity.PomodoroTimerActivity
import com.example.mynirvana.presentation.dialogs.startPomodoroDialog.StartPomodoroFragment
import com.example.mynirvana.presentation.mainFragments.productivityFragment.callback.PomodoroTimerStartCallback
import com.example.mynirvana.presentation.recycler.RecyclerViewType
import com.example.mynirvana.presentation.recycler.adapters.pomodoro.PomodoroRecyclerAdapter
import com.example.mynirvana.presentation.recycler.onClickListeners.pomodoros.PomodoroOnClickListener
import com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductivityFragment : Fragment(), PomodoroTimerStartCallback {

    private lateinit var binding: FragmentProductivityBinding
    private val viewModel: ProductivityViewModel by viewModels()

    private lateinit var readyPomodorosData: List<Pomodoro>
    private lateinit var userPomodorosData: List<Pomodoro>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductivityBinding.inflate(inflater)
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        with(binding) {
            affairsRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SideSpacingItemDecoration(60, RecyclerViewType.Horizontal))
            }

            readyPomodorosRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SideSpacingItemDecoration(60, RecyclerViewType.Horizontal))
            }

            userPomodorosRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SideSpacingItemDecoration(60, RecyclerViewType.Horizontal))
            }
        }

        addDataSetsToRecyclerViews()
    }

    private fun addDataSetsToRecyclerViews() {
        addDataSetToReadyPomodorosRecycler()
        addDataSetToUserPomodorosRecycler()
    }

    private var currentPomodoroToStart: Pomodoro? = null

    private fun addDataSetToReadyPomodorosRecycler() {
        readyPomodorosData = viewModel.getReadyPomodoros()

        binding.readyPomodorosRecycler.adapter =
            PomodoroRecyclerAdapter(readyPomodorosData, object : PomodoroOnClickListener {
                override fun onPomodoroStart(pomodoro: Pomodoro) {
                    currentPomodoroToStart = pomodoro
                    openStartPomodoroDialog()
                }

                override fun onPomodoroDelete(pomodoro: Pomodoro) {
                    TODO("Ready pomodoros cannot be deleted")
                }

            })
    }

    private fun addDataSetToUserPomodorosRecycler() {
        viewModel.pomodorosLiveData.observe(viewLifecycleOwner) {
            userPomodorosData = it

            binding.userPomodorosRecycler.adapter =
                PomodoroRecyclerAdapter(it, object : PomodoroOnClickListener {
                    override fun onPomodoroStart(pomodoro: Pomodoro) {
                        currentPomodoroToStart = pomodoro
                        openStartPomodoroDialog()
                    }

                    override fun onPomodoroDelete(pomodoro: Pomodoro) {
                        TODO("Ready pomodoros cannot be deleted")
                    }

                })
        }
    }

    private fun openStartPomodoroDialog() {
        StartPomodoroFragment().also { startPomodoroFragment ->
            startPomodoroFragment.provideCallback(this)
            currentPomodoroToStart?.let {
                startPomodoroFragment.providePomodoroName(it.name)
            }
            startPomodoroFragment.show(parentFragmentManager, startPomodoroFragment.tag)
        }
    }

    private var isNeedToStartPomodoroTimerActivity: Boolean = false

    override fun sendUserChoiceFromStartPomodoroDialog(userChoice: Boolean) {
        isNeedToStartPomodoroTimerActivity = userChoice
    }

    override fun onPomodoroStartDialogDismissed() {
        if (isNeedToStartPomodoroTimerActivity)
            currentPomodoroToStart?.let { startPomodoroTimerActivity(it) }
    }

    private fun startPomodoroTimerActivity(pomodoro: Pomodoro) {
        val intent = Intent(activity, PomodoroTimerActivity::class.java)
        intent.putExtra("POMODORO_INFO", pomodoro)

        startActivity(intent)
    }


}