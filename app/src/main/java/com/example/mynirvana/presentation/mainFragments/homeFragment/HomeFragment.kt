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
import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.databinding.FragmentHomeBinding
import com.example.mynirvana.presentation.recycler.adapters.MeditationRecyclerAdapter
import com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import com.example.mynirvana.presentation.activities.meditationCreatorActivity.MeditationCreatorActivity
import com.example.mynirvana.presentation.activities.meditationTimerActivity.MeditationTimerActivity
import com.example.mynirvana.presentation.dialogs.startMeditationDialog.StartMeditationFragmentDialog
import com.example.mynirvana.presentation.dialogs.sureToDeleteMeditation.DeleteMeditationCallback
import com.example.mynirvana.presentation.dialogs.sureToDeleteMeditation.SureToDeleteMeditationFragment
import com.example.mynirvana.presentation.dialogs.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback
import com.example.mynirvana.presentation.recycler.onClickListeners.MeditationOnClickListener
import com.example.mynirvana.presentation.recycler.RecyclerViewType
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), UserChoiceAboutMeditationFragmentDialogCallback,
    AskingForStartMeditation, DeleteMeditationCallback {

    private lateinit var readyMeditationAdapter: MeditationRecyclerAdapter
    private lateinit var userMeditationAdapter: MeditationRecyclerAdapter

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
        initRecyclerView()
        addDataSetToReadyMeditationButtons()
        addDataSetToUserMeditationButtons()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeaderQuote()

        binding.createButton.setOnClickListener {
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
                    val dialog = StartMeditationFragmentDialog()
                    dialog.provideCallback(this@HomeFragment)
                    dialog.provideMeditationName(meditation.header)

                    pickedMeditation = meditation

                    dialog.show(parentFragmentManager, dialog.tag)
                }

                override fun onMeditationSureDelete(meditation: Meditation) {}


            })

        binding.readyMeditationsRecyclerView.adapter = readyMeditationAdapter
    }


    private fun addDataSetToUserMeditationButtons() {
        viewModel.meditationButtonLiveData.observe(viewLifecycleOwner) {
            dataForUserMeditations = it
            if (dataForUserMeditations.isEmpty()) {
                userHasZeroMeditations(true)
            } else {
                userHasZeroMeditations(false)

                userMeditationAdapter =
                    MeditationRecyclerAdapter(it, object : MeditationOnClickListener {
                        override fun onMeditationStart(meditation: Meditation) {
                            val dialog = StartMeditationFragmentDialog()
                            dialog.provideCallback(this@HomeFragment)
                            dialog.provideMeditationName(meditation.header)
                            pickedMeditation = meditation
                            dialog.show(parentFragmentManager, dialog.tag)
                        }

                        override fun onMeditationSureDelete(meditation: Meditation) {
                            pickedMeditation = meditation
                            startOnMeditationDeleteDialog()
                        }

                    })
                binding.userMeditationsRecyclerView.adapter = userMeditationAdapter
            }
        }
    }

    private fun userHasZeroMeditations(isDataEmpty: Boolean) {
        if (isDataEmpty) {
            binding.userMeditationsRecyclerView.visibility = View.GONE
            binding.userHasZeroMeditations.text = "Похоже, что вы еще не создали ни одной медитации"
        } else {
            binding.userMeditationsRecyclerView.visibility = View.VISIBLE
            binding.userHasZeroMeditations.text = ""
        }
    }


    private fun initRecyclerView() {
        binding.readyMeditationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SideSpacingItemDecoration(60, RecyclerViewType.Horizontal))
        }

        binding.userMeditationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SideSpacingItemDecoration(60, RecyclerViewType.Horizontal))
        }
    }

    private fun startOnMeditationDeleteDialog() {
        val dialog = SureToDeleteMeditationFragment().also {
            it.provideCallback(this)
            pickedMeditation?.let { meditation -> it.provideMeditation(meditation) }
        }
        dialog.show(parentFragmentManager, dialog.tag)
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
        val meditationTimerActivity = MeditationTimerActivity().also { it.provideCallback(this) }
        val intent = Intent(activity, meditationTimerActivity::class.java)
        intent.putExtra("MEDITATION_INFO", meditation)
        startActivity(intent)
    }

    private var meditationThatNeedToBeStarted: Meditation? = null

    override fun asksForStartMeditation(meditation: Meditation) {
        meditationThatNeedToBeStarted = meditation
    }

    override fun onMeditationActivityDestroyed() {
        meditationThatNeedToBeStarted?.let {
            startMeditation(it)
        }
        meditationThatNeedToBeStarted = null
    }


    override fun userDecidedAboutDeletingMeditation(userChoice: Boolean) {
        if (userChoice) {
            pickedMeditation?.let { viewModel.deleteMeditationFromDataBase(it) }
            userMeditationAdapter.notifyItemChanged(
                dataForUserMeditations.indexOf(pickedMeditation)
            )
        }
    }
}