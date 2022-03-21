package com.example.mynirvana.presentation.meditation_buttons_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynirvana.databinding.LayoutButtonsListItemBinding
import com.example.mynirvana.domain.meditationbuttons.model.MeditationButton

class MeditationButtonRecyclerAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var items: List<MeditationButton> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val itemBinding =
            LayoutButtonsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MeditationButtonViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {

            is MeditationButtonViewHolder -> holder.bind(items[position])

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun submitList(meditationButtonsList: List<MeditationButton>) {
        items = meditationButtonsList
    }


    class MeditationButtonViewHolder
    constructor(itemBinding: LayoutButtonsListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private val buttonTitle = itemBinding.buttonTitle
        private val buttonTime = itemBinding.buttonTime
        private val buttonImage = itemBinding.backgroundImage

        fun bind(meditationButton: MeditationButton) {
            buttonTitle.text = meditationButton.header
            buttonTime.text = meditationButton.time
            buttonImage.setImageResource(meditationButton.imageResourceId)
        }


    }


}