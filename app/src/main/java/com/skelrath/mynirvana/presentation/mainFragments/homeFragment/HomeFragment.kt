package com.skelrath.mynirvana.presentation.mainFragments.homeFragment

import android.app.Activity.MODE_PRIVATE
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.FragmentHomeBinding
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.domain.pomodoro.model.Pomodoro
import com.skelrath.mynirvana.presentation.activities.meditations.meditationCreatorActivity.MeditationCreatorActivity
import com.skelrath.mynirvana.presentation.activities.meditations.meditationTimerActivity.MeditationTimerActivity
import com.skelrath.mynirvana.presentation.activities.pomodoros.pomodoroCreatorActivity.PomodoroCreatorActivity
import com.skelrath.mynirvana.presentation.activities.pomodoros.pomodoroTimerActivity.PomodoroTimerActivity
import com.skelrath.mynirvana.presentation.dialogs.meditation.startMeditationDialog.StartMeditationFragment
import com.skelrath.mynirvana.presentation.dialogs.pomodoro.startPomodoroDialog.StartPomodoroFragment
import com.skelrath.mynirvana.presentation.dialogs.userDeleteDialog.UserDeleteFragment
import com.skelrath.mynirvana.presentation.recycler.RecyclerViewType
import com.skelrath.mynirvana.presentation.recycler.adapters.meditation.MeditationRecyclerAdapter
import com.skelrath.mynirvana.presentation.recycler.adapters.pomodoro.PomodoroRecyclerAdapter
import com.skelrath.mynirvana.presentation.recycler.onClickListeners.meditations.MeditationOnClickListener
import com.skelrath.mynirvana.presentation.recycler.onClickListeners.pomodoros.PomodoroOnClickListener
import com.skelrath.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var readyMeditationAdapter: MeditationRecyclerAdapter
    private lateinit var userMeditationAdapter: MeditationRecyclerAdapter
    private lateinit var userPomodorosAdapter: PomodoroRecyclerAdapter

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeFragmentViewModel by viewModels()

    private lateinit var readyMeditationsData: List<Meditation>
    private lateinit var userMeditationsData: List<Meditation>
    private lateinit var readyPomodorosData: List<Pomodoro>
    private lateinit var userPomodorosData: List<Pomodoro>

    private var meditationToStart: Meditation? = null
    private var meditationToDelete: Meditation? = null

    private var pomodoroToStart: Pomodoro? = null
    private var pomodoroToDelete: Pomodoro? = null

    private var launcherForMeditationTimerActivity: ActivityResultLauncher<Intent>? = null
    private var launcherForPomodoroTimerActivity: ActivityResultLauncher<Intent>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        initRecyclerView()
        initButtons()
        initMeditationTimerActivityLauncher()
        initPomodoroTimerActivityLauncher()
        initHeaderGreetingsAndQuote()

        return binding.root
    }

    private fun initMeditationTimerActivityLauncher() {
        launcherForMeditationTimerActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    meditationToStart =
                        result.data?.getSerializableExtra("MEDITATION_TO_START") as Meditation?
                    meditationToStart?.let { startMeditationTimerActivity(it) }
                }
            }
    }

    private fun initPomodoroTimerActivityLauncher() {
        launcherForPomodoroTimerActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    pomodoroToStart =
                        result.data?.getSerializableExtra("POMODORO_TO_START") as Pomodoro?
                    pomodoroToStart?.let { startPomodoroTimerActivity(it) }
                }
            }
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
        Intent(activity, MeditationCreatorActivity::class.java).also {
            launcherForMeditationTimerActivity?.launch(it)
        }
    }

    private fun startPomodoroCreatorActivity() {
        Intent(activity, PomodoroCreatorActivity::class.java).also {
            launcherForPomodoroTimerActivity?.launch(it)
        }
    }

    private fun initHeaderGreetingsAndQuote() {
        val sharedPreferences = requireContext().getSharedPreferences("shared_pref", MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            binding.greetingsHeaderTV.text = sharedPreferences.getString(
                key, "Путник"
            )
        }

        viewModel.userNameLiveData.observe(viewLifecycleOwner) {
            binding.greetingsHeaderTV.text = "Здравствуй, $it!"
        }

        val quotesArray = resources.getStringArray(R.array.quotes_array)
        val currentQuote = quotesArray.random()
        binding.quoteTV.text = currentQuote
    }


    private fun addDataSetToReadyMeditationButtons() {
        readyMeditationsData = viewModel.getReadyMeditations()
        readyMeditationAdapter = MeditationRecyclerAdapter(
            readyMeditationsData,
            object : MeditationOnClickListener {
                override fun onMeditationStart(meditation: Meditation) {
                    meditationToStart = meditation
                    openStartMeditationDialog()
                }

                override fun onMeditationDelete(meditation: Meditation) {
                    TODO("Ready meditations cannot be deleted")
                }
            })

        binding.readyMeditationsRecyclerView.adapter = readyMeditationAdapter
    }


    private fun addDataSetToUserMeditationButtons() {
        viewModel.meditationLiveData.observe(viewLifecycleOwner) {
            userMeditationsData = it
            initUserHasZeroMeditationsTextView(userMeditationsData.isEmpty())

            userMeditationAdapter =
                MeditationRecyclerAdapter(it, object : MeditationOnClickListener {
                    override fun onMeditationStart(meditation: Meditation) {
                        meditationToStart = meditation
                        openStartMeditationDialog()
                    }

                    override fun onMeditationDelete(meditation: Meditation) {
                        meditationToDelete = meditation
                        userMeditationAdapter.notifyItemChanged(it.indexOf(meditation))
                        openDeleteMeditationDialog()
                    }

                })

            binding.userMeditationsRecyclerView.adapter = userMeditationAdapter
        }
    }

    private fun initUserHasZeroMeditationsTextView(isDataEmpty: Boolean) {
        if (isDataEmpty)
            binding.userHasZeroMeditations.text =
                "Похоже, что вы еще не создали ни одной медитации"
        else
            binding.userHasZeroMeditations.text = ""
    }


    private fun addDataSetToReadyPomodorosRecycler() {
        readyPomodorosData = viewModel.getReadyPomodoros()

        binding.readyPomodorosRecycler.adapter =
            PomodoroRecyclerAdapter(readyPomodorosData, object : PomodoroOnClickListener {
                override fun onPomodoroStart(pomodoro: Pomodoro) {
                    pomodoroToStart = pomodoro
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
            initUserHasZeroPomodorosTextView(userPomodorosData.isEmpty())

            userPomodorosAdapter =
                PomodoroRecyclerAdapter(it, object : PomodoroOnClickListener {
                    override fun onPomodoroStart(pomodoro: Pomodoro) {
                        pomodoroToStart = pomodoro
                        openStartPomodoroDialog()
                    }

                    override fun onPomodoroDelete(pomodoro: Pomodoro) {
                        pomodoroToDelete = pomodoro
                        openDeletePomodoroDialog()
                    }

                })
            binding.userPomodorosRecycler.adapter = userPomodorosAdapter
        }
    }

    private fun initUserHasZeroPomodorosTextView(isUserHasZeroPomodoros: Boolean) {
        if (isUserHasZeroPomodoros)
            binding.userHasZeroPomodorosHomeFragmentTV.text =
                "Похоже, что вы еще не создали ни одного помодоро таймера"
        else binding.userHasZeroPomodorosHomeFragmentTV.text = ""
    }


    private fun initRecyclerView() {
        with(binding) {
            readyMeditationsRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SideSpacingItemDecoration(60, RecyclerViewType.Horizontal))
            }

            userMeditationsRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SideSpacingItemDecoration(60, RecyclerViewType.Horizontal))
            }

            readyPomodorosRecycler.apply {
                layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(SideSpacingItemDecoration(60, RecyclerViewType.Horizontal))
            }

            userPomodorosRecycler.apply {
                layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
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
        StartMeditationFragment().also { startMeditationFragment ->
            startMeditationFragment.provideLambdaCallback { userChoice: Boolean ->
                if (userChoice)
                    meditationToStart?.let { startMeditationTimerActivity(it) }
            }
            meditationToStart?.name?.let { startMeditationFragment.provideMeditationName(it) }
            startMeditationFragment.show(parentFragmentManager, startMeditationFragment.tag)
        }
    }

    private fun openDeleteMeditationDialog() {
        UserDeleteFragment().also { userDeleteFragment ->
            userDeleteFragment.provideLambdaCallback { userChoice: Boolean ->
                if (userChoice) {
                    meditationToDelete?.let {
                        viewModel.deleteMeditation(it)
                    }

                    userMeditationAdapter.notifyItemChanged(
                        userMeditationsData.indexOf(
                            meditationToDelete
                        )
                    )
                }
            }

            meditationToDelete?.let { meditation ->
                userDeleteFragment.provideMeditation(
                    meditation
                )
            }
            userDeleteFragment.show(parentFragmentManager, userDeleteFragment.tag)
        }
    }

    private fun openStartPomodoroDialog() {
        StartPomodoroFragment().also { startPomodoroFragment ->
            startPomodoroFragment.provideLambdaCallback { userChoice: Boolean ->
                if (userChoice)
                    pomodoroToStart?.let { startPomodoroTimerActivity(it) }
            }
            pomodoroToStart?.let {
                startPomodoroFragment.providePomodoroName(it.name!!)
            }
            startPomodoroFragment.show(parentFragmentManager, startPomodoroFragment.tag)
        }
    }

    private fun openDeletePomodoroDialog() {
        UserDeleteFragment().also { userDeleteFragment ->
            userDeleteFragment.provideLambdaCallback { userChoice: Boolean ->
                if (userChoice) {
                    pomodoroToDelete?.let {
                        viewModel.deletePomodoro(it)
                    }

                    userPomodorosAdapter.notifyItemChanged(
                        userPomodorosData.indexOf(
                            pomodoroToDelete
                        )
                    )
                }
            }

            pomodoroToDelete?.let {
                userDeleteFragment.providePomodoro(it)
            }

            userDeleteFragment.show(parentFragmentManager, userDeleteFragment.tag)
        }
    }


    private fun startMeditationTimerActivity(meditation: Meditation) {
        Intent(activity, MeditationTimerActivity::class.java).also {
            it.putExtra("MEDITATION_INFO", meditation)
            launcherForMeditationTimerActivity?.launch(it)
        }
    }

    private fun startPomodoroTimerActivity(pomodoro: Pomodoro) {
        Intent(activity, PomodoroTimerActivity::class.java).also {
            it.putExtra("POMODORO_INFO", pomodoro)
            startActivity(it)
        }
    }
}