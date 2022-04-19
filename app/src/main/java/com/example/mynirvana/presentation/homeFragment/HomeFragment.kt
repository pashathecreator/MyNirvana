package com.example.mynirvana.presentation.homeFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynirvana.databinding.FragmentHomeBinding
import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.presentation.meditationButtonsRecycler.MeditationButtonRecyclerAdapter
import com.example.mynirvana.presentation.meditationButtonsRecycler.MeditationOnClickListener
import com.example.mynirvana.presentation.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import com.example.mynirvana.presentation.meditationCreatorActivity.MeditationCreatorActivity
import com.example.mynirvana.presentation.meditationTimerActivity.MeditationTimerActivity
import com.example.mynirvana.presentation.dialogs.startMeditationDialog.StartMeditationFragmentDialog
import com.example.mynirvana.presentation.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), UserChoiceAboutMeditationFragmentDialogCallback,
    AskingForStartMeditation {

    private lateinit var readyMeditationButtonAdapter: MeditationButtonRecyclerAdapter
    private lateinit var userMeditationButtonAdapter: MeditationButtonRecyclerAdapter

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeFragmentViewModel by viewModels()

    private lateinit var dataForReadyMeditations: List<Meditation>
    private lateinit var dataForUserMeditations: List<Meditation>

    private var isMeditationNeedToBeStarted: Boolean = false
    private var isAskingForStartMeditation: Boolean = false

    private var pickedMeditation: Meditation? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater)

        initRecyclerView(binding)
        addDataSetToReadyMeditationButtons()
        addDataSetToUserMeditationButtons()


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createButton.setOnClickListener {
            val intent = Intent(activity, MeditationCreatorActivity::class.java)
            startActivity(intent)
        }


    }


    private fun addDataSetToReadyMeditationButtons() {
        dataForReadyMeditations = viewModel.getReadyMeditations()
        readyMeditationButtonAdapter = MeditationButtonRecyclerAdapter(
            dataForReadyMeditations,
            object : MeditationOnClickListener {
                override fun onMeditationStart(meditation: Meditation) {
                    val dialog = StartMeditationFragmentDialog()
                    dialog.provideCallback(this@HomeFragment)
                    dialog.provideMeditationName(meditation.header)

                    pickedMeditation = meditation

                    dialog.show(parentFragmentManager, dialog.tag)
                }

                override fun onMeditationDelete(meditation: Meditation) {}

                override fun onMeditationSureDelete(meditation: Meditation) {}


            })

        binding.readyMeditationsRecyclerView.adapter = readyMeditationButtonAdapter
    }


    private fun addDataSetToUserMeditationButtons() {
        viewModel.meditationButtonLiveData.observe(viewLifecycleOwner) {
            dataForUserMeditations = it
            if (dataForUserMeditations.isEmpty()) {
                userHasZeroMeditations(true)
            } else {
                userHasZeroMeditations(false)

                userMeditationButtonAdapter =
                    MeditationButtonRecyclerAdapter(it, object : MeditationOnClickListener {
                        override fun onMeditationStart(meditation: Meditation) {
                            val dialog = StartMeditationFragmentDialog()
                            dialog.provideCallback(this@HomeFragment)
                            dialog.provideMeditationName(meditation.header)

                            pickedMeditation = meditation

                            dialog.show(parentFragmentManager, dialog.tag)
                        }

                        override fun onMeditationDelete(meditation: Meditation) {
                            meditation.isMeditationOnDelete = true
                            readyMeditationButtonAdapter.notifyDataSetChanged()
                        }

                        override fun onMeditationSureDelete(meditation: Meditation) {
                            meditation.isMeditationOnDelete = true
                            readyMeditationButtonAdapter.notifyDataSetChanged()
                        }

                    })

                binding.userMeditationsRecyclerView.adapter = userMeditationButtonAdapter
            }
        }
    }

    private fun userHasZeroMeditations(isDataEmpty: Boolean) {
        if (isDataEmpty)
            binding.userHasZeroMeditations.text = "Похоже, что вы еще не создали ни одной медитации"
        else
            binding.userHasZeroMeditations.text = ""
    }


    private fun initRecyclerView(binding: FragmentHomeBinding) {
        binding.readyMeditationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SideSpacingItemDecoration(60))
        }

        binding.userMeditationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SideSpacingItemDecoration(60))
        }

    }

    override fun sendUserChoice(userChoice: Boolean) {
        this.isMeditationNeedToBeStarted = userChoice
    }

    override fun fragmentDismissed() {
        if (isMeditationNeedToBeStarted)
            pickedMeditation?.let { startMeditation(it) }

    }

    private fun startMeditation(meditation: Meditation) {
        val intent = Intent(activity, MeditationTimerActivity::class.java)
        intent.putExtra("MEDITATION_INFO", meditation)

        startActivity(intent)
    }

    override fun asksForStartMeditation(meditation: Meditation) {
        startMeditation(meditation)
    }

}