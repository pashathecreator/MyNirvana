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
import com.example.mynirvana.presentation.meditationButtonsRecycler.MeditationButtonRecyclerAdapter
import com.example.mynirvana.presentation.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import com.example.mynirvana.presentation.meditationCreatorActivity.MeditationCreatorActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var meditationButtonAdapter: MeditationButtonRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater)

        initRecyclerView(binding = binding)
        addDataSetToReadyMeditationButtons()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createButton.setOnClickListener {

            val intent = Intent(activity, MeditationCreatorActivity::class.java)

            startActivity(intent)


//            findNavController().navigate(
//                R.id.action_homeFragment_to_meditationCreatorFragment, null,
//                navOptions {
//                    this.anim {
//                        enter = androidx.navigation.ui.R.anim.nav_default_enter_anim
//                        popEnter = androidx.navigation.ui.R.anim.nav_default_enter_anim
//                        popExit = androidx.navigation.ui.R.anim.nav_default_exit_anim
//                        exit = androidx.navigation.ui.R.anim.nav_default_enter_anim
//                    }
//                })
        }

    }

    private fun addDataSetToReadyMeditationButtons() {
        val data = viewModel.getReadyMeditations()
        meditationButtonAdapter.submitList(data)
    }


    private fun initRecyclerView(binding: FragmentHomeBinding) {
        binding.readyMeditationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SideSpacingItemDecoration(60))

            meditationButtonAdapter = MeditationButtonRecyclerAdapter()
            adapter = meditationButtonAdapter


        }

    }

}