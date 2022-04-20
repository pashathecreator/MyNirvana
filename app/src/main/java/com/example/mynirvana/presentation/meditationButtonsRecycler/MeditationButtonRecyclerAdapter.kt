package com.example.mynirvana.presentation.meditationButtonsRecycler

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.mynirvana.R
import com.example.mynirvana.databinding.LayoutButtonsListItemBinding
import com.example.mynirvana.domain.meditations.model.Meditation

interface MeditationOnClickListener {
    fun onMeditationStart(meditation: Meditation)
    fun onMeditationDelete(meditation: Meditation)
    fun onMeditationSureDelete(meditation: Meditation)
}


class MeditationButtonRecyclerAdapter(
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
            if (meditation.isMeditationOnDelete) {
                itemBinding.shadingLayout.background = (R.color.purple_700).toDrawable()
                itemBinding.shadingLayout.findViewById<ImageButton>(R.id.trashCanButton)
                    .visibility = View.VISIBLE
                itemBinding.shadingLayout.findViewById<ImageButton>(R.id.trashCanButton)
                    .setImageResource(R.drawable.ic_trashcan_icon)
                itemBinding.shadingLayout.findViewById<ImageButton>(R.id.trashCanButton)
                    .setOnClickListener {
                        actionListener.onMeditationSureDelete(meditation)
                    }

            } else {
                itemBinding.root.tag = meditation
                buttonTitle.text = meditation.header
                val minutes = (meditation.time / 60).toInt()
                val seconds = meditation.time % 60
                val secondsToString = if (seconds < 10) "0$seconds" else seconds.toString()
                val minuteWord = when (minutes) {
                    1 -> "минута"
                    2, 3, 4 -> "минуты"
                    else -> "минут"
                }
                buttonTime.text =
                    "$minutes:$secondsToString $minuteWord"
                buttonImage.setImageResource(meditation.imageResourceId)

                itemBinding.root.setOnClickListener {
                    actionListener.onMeditationStart(meditation)
                }
                itemBinding.root.setOnLongClickListener {
                    actionListener.onMeditationDelete(meditation)
                    true
                }
            }
        }


    }
}