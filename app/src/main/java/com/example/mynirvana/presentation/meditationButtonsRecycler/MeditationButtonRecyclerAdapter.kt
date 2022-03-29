package com.example.mynirvana.presentation.meditationButtonsRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynirvana.databinding.LayoutButtonsListItemBinding
import com.example.mynirvana.domain.meditations.model.Meditation

class MeditationButtonRecyclerAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var items: List<Meditation> = ArrayList()

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


    fun submitList(meditationButtonsList: List<Meditation>) {
        items = meditationButtonsList
    }


    class MeditationButtonViewHolder(itemBinding: LayoutButtonsListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private val buttonTitle = itemBinding.buttonTitle
        private val buttonTime = itemBinding.buttonTime
        private val buttonImage = itemBinding.backgroundImage

        fun bind(meditationButton: Meditation) {
            buttonTitle.text = meditationButton.header

            val minutes = (meditationButton.time / 60).toInt()
            val seconds = meditationButton.time % 60
            val secondsToString = if (seconds < 10) "0$seconds" else seconds.toString()
            val minuteWord = when (minutes) {
                1 -> "минута"
                2, 3, 4 -> "минуты"
                else -> "минут"
            }
            buttonTime.text =
                "$minutes:$secondsToString $minuteWord"
            buttonImage.setImageResource(meditationButton.imageResourceId)
        }


    }


}