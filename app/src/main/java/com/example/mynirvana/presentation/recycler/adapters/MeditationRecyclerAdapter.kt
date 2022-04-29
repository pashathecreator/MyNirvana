package com.example.mynirvana.presentation.recycler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.mynirvana.R
import com.example.mynirvana.databinding.LayoutButtonsListItemBinding
import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.presentation.recycler.onClickListeners.MeditationOnClickListener


class MeditationRecyclerAdapter(
    private val items: List<Meditation> = ArrayList(),
    private val actionListener: MeditationOnClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val itemBinding =
            LayoutButtonsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeditationButtonViewHolder(itemBinding, actionListener)
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


    class MeditationButtonViewHolder(
        private val itemBinding: LayoutButtonsListItemBinding,
        private val actionListener: MeditationOnClickListener
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private val buttonTitle = itemBinding.buttonTitle
        private val buttonTime = itemBinding.buttonTime
        private val buttonImage = itemBinding.backgroundImage

        fun bind(meditation: Meditation) {
            itemBinding.root.tag = meditation

            buttonTitle.text = meditation.header
            buttonImage.setImageResource(meditation.imageResourceId)
            val minutes = (meditation.time / 60).toInt()
            val seconds = meditation.time % 60
            val secondsToString = if (seconds < 10) "0$seconds" else seconds.toString()
            val timeWord = when {
                minutes < 1 -> when (seconds) {
                    1L -> "секунда"
                    in 2..4 -> "секунды"
                    else -> "секунд"
                }
                minutes == 1 -> "минута"
                minutes in 2..4 -> "минуты"
                else -> "минут"
            }
            buttonTime.text =
                "$minutes:$secondsToString $timeWord"

            itemBinding.root.setOnClickListener {
                actionListener.onMeditationStart(meditation)
            }
            if (meditation.isMeditationCanBeDeleted) {
                itemBinding.root.setOnLongClickListener {
                    meditationOnDelete(meditation)
                    true
                }
            }
        }

        private fun meditationOnDelete(meditation: Meditation) {
            with(itemBinding) {
                shadingLayout.visibility = View.VISIBLE
                deleteMeditaitonTV.setOnClickListener {
                    actionListener.onMeditationSureDelete(meditation)
                }
                shadingLayout.setOnLongClickListener {
                    meditationOnRevert()
                    true
                }
            }
        }

        private fun meditationOnRevert() {
            with(itemBinding) {
                shadingLayout.visibility = View.GONE
            }
        }
    }
}
