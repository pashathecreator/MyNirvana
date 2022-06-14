package com.skelrath.mynirvana.presentation.recycler.adapters.meditation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skelrath.mynirvana.databinding.LayoutBigButtonsListItemBinding
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.presentation.recycler.onClickListeners.meditations.MeditationOnClickListener

class BigMeditationRecyclerAdapter(
    private val items: List<Meditation>,
    private val actionListener: MeditationOnClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            LayoutBigButtonsListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return BigMeditationViewHolder(itemBinding, actionListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BigMeditationViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class BigMeditationViewHolder(
        private val itemBinding: LayoutBigButtonsListItemBinding,
        private val actionListener: MeditationOnClickListener
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(meditation: Meditation) {
            itemBinding.root.tag = meditation

            itemBinding.buttonTitle.text = meditation.name
            itemBinding.backgroundImage.setImageResource(meditation.imageResourceId!!)
            val minutes = (meditation.time!! / 60).toInt()
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
            itemBinding.buttonTime.text =
                "$minutes:$secondsToString $timeWord"
            itemBinding.root.setOnClickListener {
                actionListener.onMeditationStart(meditation)
            }

            meditationOnRevert()
            if (meditation.isMeditationCanBeDeleted!!) {
                itemBinding.root.setOnLongClickListener {
                    meditationOnDelete(meditation)
                    true
                }
            }
            if (meditation.isMeditationCompleted!!) {
                meditationCompleted()
            }
        }

        private fun meditationCompleted() {
            with(itemBinding) {
                shadingLayout.visibility = View.VISIBLE
                root.isClickable = false
                actionTV.text = "Медитация выполнена"
            }
        }

        private fun meditationOnDelete(meditation: Meditation) {
            with(itemBinding) {
                shadingLayout.visibility = View.VISIBLE
                shadingLayout.setOnClickListener {
                    actionListener.onMeditationDelete(meditation)
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