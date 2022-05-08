package com.example.mynirvana.presentation.mainFragments.homeFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynirvana.R
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.databinding.FragmentHomeBinding
import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.presentation.recycler.adapters.meditation.MeditationRecyclerAdapter
import com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import com.example.mynirvana.presentation.activities.meditations.meditationCreatorActivity.MeditationCreatorActivity
import com.example.mynirvana.presentation.activities.meditations.meditationTimerActivity.MeditationTimerActivity
import com.example.mynirvana.presentation.activities.pomodoros.pomodoroCreatorActivity.PomodoroCreatorActivity
import com.example.mynirvana.presentation.activities.pomodoros.pomodoroTimerActivity.PomodoroTimerActivity
import com.example.mynirvana.presentation.dialogs.startMeditationDialog.StartMeditationFragment
import com.example.mynirvana.presentation.dialogs.startPomodoroDialog.StartPomodoroFragment
import com.example.mynirvana.presentation.dialogs.userDeleteDialog.UserDeleteMeditationCallback
import com.example.mynirvana.presentation.dialogs.userDeleteDialog.UserDeleteFragment
import com.example.mynirvana.presentation.dialogs.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback
import com.example.mynirvana.presentation.dialogs.userDeleteDialog.UserDeletePomodoroCallback
import com.example.mynirvana.presentation.mainFragments.productivityFragment.callback.AskingToStartPomodoroTimer
import com.example.mynirvana.presentation.mainFragments.productivityFragment.callback.PomodoroTimerStartCallback
import com.example.mynirvana.presentation.recycler.onClickListeners.meditations.MeditationOnClickListener
import com.example.mynirvana.presentation.recycler.RecyclerViewType
import com.example.mynirvana.presentation.recycler.adapters.pomodoro.PomodoroRecyclerAdapter
import com.example.mynirvana.presentation.recycler.onClickListeners.pomodoros.PomodoroOnClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), UserChoiceAboutMeditationFragmentDialogCallback,
    AskingForStartMeditation, UserDeleteMeditationCallback, PomodoroTimerStartCallback,
    AskingToStartPomodoroTimer, UserDeletePomodoroCallback {

    private lateinit var readyMeditationAdapter: MeditationRecyclerAdapter
    private lateinit var userMeditationAdapter: MeditationRecyclerAdapter
    private lateinit var userPomodorosAdapter: PomodoroRecyclerAdapter

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeFragmentViewModel by viewModels()

    private lateinit var dataForReadyMeditations: List<Meditation>
    private lateinit var dataForUserMeditations: List<Meditation>
    private lateinit var readyPomodorosData: List<Pomodoro>
    private lateinit var userPomodorosData: List<Pomodoro>

    private var isMeditationNeedToBeStarted: Boolean = false
    private var currentMeditationThatNeedToBeStarted: Meditation? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        initHeaderQuote()
        initRecyclerView()
        initButtons()

        return binding.root
    }

    private fun initButtons() {
        with(binding) {
            createMeditationHomeFragmentButton.setOnClickListener {
                startMeditationCreatorActivity()
            }

            createPomodoroTimerHomeFragmentButton.setOnClickListener {
                startPomodoroCreatorActivity()
            }
        }
    }

    private fun startMeditationCreatorActivity() {
        MeditationCreatorActivity().also {
            it.provideCallback(
                this
            )
            val intent = Intent(activity, it::class.java)
            startActivity(intent)
        }
    }

    private fun startPomodoroCreatorActivity() {
        PomodoroCreatorActivity().also {
            it.provideCallback(this)
            val intent = Intent(activity, it::class.java)
            startActivity(intent)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeaderQuote()

        binding.createMeditationHomeFragmentButton.setOnClickListener {
            val meditationCreatorActivity = MeditationCreatorActivity()
            meditationCreatorActivity.provideCallback(
                this
            )
            val intent = Intent(activity, meditationCreatorActivity::class.java)
            startActivity(intent)
        }


    }


    private fun initHeaderQuote() {
        val quotesArray = resources.getStringArray(R.array.quotes_array)
        val currentQuote = quotesArray.random()
        binding.quoteTV.text = currentQuote
    }


    private fun addDataSetToReadyMeditationButtons() {
        dataForReadyMeditations = viewModel.getReadyMeditations()
        readyMeditationAdapter = MeditationRecyclerAdapter(
            dataForReadyMeditations,
            object : MeditationOnClickListener {
                override fun onMeditationStart(meditation: Meditation) {
                    openStartMeditationDialog()
                    currentMeditationThatNeedToBeStarted = meditation
                }

                override fun onMeditationDelete(meditation: Meditation) {
                    TODO("Ready meditations cannot be deleted")
                }


            })

        binding.readyMeditationsRecyclerView.adapter = readyMeditationAdapter
    }


    private fun addDataSetToUserMeditationButtons() {
        viewModel.meditationLiveData.observe(viewLifecycleOwner) {
            dataForUserMeditations = it
            initUserHasZeroMeditationsTextView(dataForUserMeditations.isEmpty())

            userMeditationAdapter =
                MeditationRecyclerAdapter(it, object : MeditationOnClickListener {
                    override fun onMeditationStart(meditation: Meditation) {
                        val dialog = StartMeditationFragment()
                        dialog.provideCallback(this@HomeFragment)
                        dialog.provideMeditationName(meditation.name)
                        currentMeditationThatNeedToBeStarted = meditation
                        dialog.show(parentFragmentManager, dialog.tag)
                    }

                    override fun onMeditationDelete(meditation: Meditation) {
                        currentMeditationThatNeedToBeStarted = meditation
                        openDeleteMeditationDialog()
                    }

                })
            binding.userMeditationsRecyclerView.adapter = userMeditationAdapter

        }
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

    private var pomodoroThatNeedToBeDeleted: Pomodoro? = null

    private fun addDataSetToUserPomodorosRecycler() {
        viewModel.pomodorosLiveData.observe(viewLifecycleOwner) {
            userPomodorosData = it
            initUserHasZeroPomodorosTextView(userPomodorosData.isEmpty())

            userPomodorosAdapter =
                PomodoroRecyclerAdapter(it, object : PomodoroOnClickListener {
                    override fun onPomodoroStart(pomodoro: Pomodoro) {
                        currentPomodoroToStart = pomodoro
                        openStartPomodoroDialog()
                    }

                    override fun onPomodoroDelete(pomodoro: Pomodoro) {
                        pomodoroThatNeedToBeDeleted = pomodoro
                        openDeletePomodoroDialog()
                    }

                })
            binding.userPomodorosRecycler.adapter = userPomodorosAdapter
        }
    }

    private fun initUserHasZeroMeditationsTextView(isDataEmpty: Boolean) {
        if (isDataEmpty)
            binding.userHasZeroMeditations.text = "Похоже, что вы еще не создали ни одной медитации"
        else
            binding.userHasZeroMeditations.text = ""

    }

    private fun initUserHasZeroPomodorosTextView(isUserHasZeroPomodoros: Boolean) {
        if (isUserHasZeroPomodoros)
            binding.userHasZeroPomodorosHomeFragmentTV.text =
                "Похоже, что вы еще не создали ни одного помодоро таймера"
        else binding.userHasZeroPomodorosHomeFragmentTV.text =
            ""
    }


    private fun initRecyclerView() {
        with(binding) {
            readyMeditationsRecyclerView.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SideSpacingItemDecoration(60, RecyclerViewType.Horizontal))
            }

            userMeditationsRecyclerView.apply {
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
        addDataSetToReadyMeditationButtons()
        addDataSetToUserMeditationButtons()
        addDataSetToReadyPomodorosRecycler()
        addDataSetToUserPomodorosRecycler()
    }

    private fun openStartMeditationDialog() {
        StartMeditationFragment().also {
            it.provideCallback(this)
            meditationThatNeedToBeStarted?.name?.let { meditation ->
                it.provideMeditationName(meditation)
            }
            it.show(parentFragmentManager, it.tag)
        }
    }

    private fun openDeleteMeditationDialog() {
        UserDeleteFragment().also {
            it.provideCallbackForMeditation(this)
            currentMeditationThatNeedToBeStarted?.let { meditation ->
                it.provideMeditation(
                    meditation
                )
            }
            it.show(parentFragmentManager, it.tag)
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

    private fun openDeletePomodoroDialog() {
        UserDeleteFragment().also { userDeleteFragment ->
            userDeleteFragment.provideCallbackForPomodoro(this)
            pomodoroThatNeedToBeDeleted?.let {
                userDeleteFragment.providePomodoro(it)
            }
            userDeleteFragment.show(parentFragmentManager, userDeleteFragment.tag)
        }
    }

    override fun sendUserChoiceFromFragmentDialog(userChoice: Boolean) {
        this.isMeditationNeedToBeStarted = userChoice
    }

    override fun userChoiceFragmentDialogDismissed() {
        if (isMeditationNeedToBeStarted)
            currentMeditationThatNeedToBeStarted?.let { startMeditationTimerActivity(it) }
    }

    private fun startMeditationTimerActivity(meditation: Meditation) {
        MeditationTimerActivity().also {
            it.provideCallbackForFragment(this)
            val intent = Intent(activity, it::class.java)
            intent.putExtra("MEDITATION_INFO", meditation)
            startActivity(intent)
        }
    }

    private fun startPomodoroTimerActivity(pomodoro: Pomodoro) {
        val intent = Intent(activity, PomodoroTimerActivity::class.java)
        intent.putExtra("POMODORO_INFO", pomodoro)

        startActivity(intent)
    }

    private var meditationThatNeedToBeStarted: Meditation? = null

    override fun asksForStartMeditation(meditation: Meditation) {
        meditationThatNeedToBeStarted = meditation
    }

    override fun onReadyToStartMeditation() {
        meditationThatNeedToBeStarted?.let {
            startMeditationTimerActivity(it)
        }
        meditationThatNeedToBeStarted = null
    }

    private var pomodoroThatNeedToBeStarted: Pomodoro? = null

    override fun asksToStartPomodoroTimer(pomodoro: Pomodoro) {
        pomodoroThatNeedToBeStarted = pomodoro
    }

    override fun onReadyToStartPomodoroTimer() {
        pomodoroThatNeedToBeStarted?.let {
            startPomodoroTimerActivity(it)
        }
    }


    override fun userDecidedAboutDeletingMeditation(userChoice: Boolean) {
        if (userChoice)
            currentMeditationThatNeedToBeStarted?.let { viewModel.deleteMeditationFromDataBase(it) }

        userMeditationAdapter.notifyItemChanged(
            dataForUserMeditations.indexOf(
                currentMeditationThatNeedToBeStarted
            )
        )
    }

    override fun userDecidedAboutDeletingPomodoro(userChoice: Boolean) {
        if (userChoice)
            pomodoroThatNeedToBeDeleted?.let { viewModel.deletePomodoro(it) }

        userPomodorosAdapter.notifyItemChanged(userPomodorosData.indexOf(pomodoroThatNeedToBeDeleted))
    }

    private var isNeedToStartPomodoroTimerActivity: Boolean = false

    override fun sendUserChoiceFromStartPomodoroDialog(userChoice: Boolean) {
        isNeedToStartPomodoroTimerActivity = userChoice
    }

    override fun onPomodoroStartDialogDismissed() {
        if (isNeedToStartPomodoroTimerActivity)
            currentPomodoroToStart?.let { startPomodoroTimerActivity(it) }
    }


}