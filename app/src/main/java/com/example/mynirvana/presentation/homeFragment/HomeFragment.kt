package com.example.mynirvana.presentation.homeFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynirvana.R
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
import java.io.Serializable


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

    private var pickedMeditation: Meditation? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater)

        initRecyclerView(binding = binding)
        addDataSetToReadyMeditationButtons()
        addDataSetToUserMeditationButtons()


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createButton.setOnClickListener {
            startMeditationCreatorActivity()
        }
    }

    private fun startMeditationCreatorActivity() {
        val meditationCreatorActivity =
            MeditationCreatorActivity().also { it.provideCallback(this) }
        val intent = Intent(activity, meditationCreatorActivity::class.java)
        startActivity(intent)
    }


    private fun addDataSetToReadyMeditationButtons() {
        dataForReadyMeditations = viewModel.getReadyMeditations()
        readyMeditationButtonAdapter = MeditationButtonRecyclerAdapter(
            dataForReadyMeditations,
            object : MeditationOnClickListener {
                override fun onMeditationStart(meditation: Meditation) {
                    val dialog = StartMeditationFragmentDialog().also {
                        it.provideCallback(this@HomeFragment)
                        it.provideMeditationName(meditation.header)
                    }

                    pickedMeditation = meditation

                    dialog.show(parentFragmentManager, dialog.tag)
                }

                override fun onMeditationDelete(meditation: Meditation) {
                    TODO("Not yet implemented")
                }

            })

        binding.readyMeditationsRecyclerView.adapter = readyMeditationButtonAdapter
    }

    private fun addDataSetToUserMeditationButtons() {
        viewModel.meditationButtonLiveData.observe(viewLifecycleOwner) {
            dataForUserMeditations = it
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
                        TODO("Not yet implemented")
                    }

                })

            binding.userMeditationsRecyclerView.adapter = userMeditationButtonAdapter
        }
    }


    private fun initRecyclerView(binding: FragmentHomeBinding) {
        binding.readyMeditationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SideSpacingItemDecoration(60))

//            readyMeditationButtonAdapter = MeditationButtonRecyclerAdapter(dataForReadyMeditations)
//            adapter = readyMeditationButtonAdapter
        }

        binding.userMeditationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SideSpacingItemDecoration(60))

//            userMeditationButtonAdapter = MeditationButtonRecyclerAdapter()
//            adapter = userMeditationButtonAdapter
        }

    }

    override fun sendUserChoiceFromFragmentDialog(userChoice: Boolean) {
        this.isMeditationNeedToBeStarted = userChoice
    }

    override fun userChoiceFragmentDialogDismissed(isDismissedByCrossButton: Boolean) {
        if (!isDismissedByCrossButton) {
            if (isMeditationNeedToBeStarted)
                pickedMeditation?.let { startMeditation(it) }
        }

    }

    private fun startMeditation(meditation: Meditation) {
        val intent = Intent(activity, MeditationTimerActivity::class.java)
        intent.putExtra("MEDITATION_INFO", meditation)

        startActivity(intent)
    }

    private var meditationThatNeedToBeStartedBecauseMeditationCreatorAskingForStartMeditation: Meditation? =
        null

    override fun asksForStartMeditation(meditation: Meditation) {
        meditationThatNeedToBeStartedBecauseMeditationCreatorAskingForStartMeditation = meditation
    }

    override fun onMeditationCreatorActivityDestroyed() {
        meditationThatNeedToBeStartedBecauseMeditationCreatorAskingForStartMeditation?.let {
            startMeditation(it)
        }
    }

}