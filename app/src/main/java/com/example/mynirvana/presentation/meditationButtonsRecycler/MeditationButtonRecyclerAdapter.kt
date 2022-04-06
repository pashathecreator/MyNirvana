package com.example.mynirvana.presentation.meditationButtonsRecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynirvana.databinding.LayoutButtonsListItemBinding
import com.example.mynirvana.domain.meditations.model.Meditation

interface MeditationOnClickListener {
    fun onMeditationStart(meditation: Meditation)
    fun onMeditationDelete(meditation: Meditation)
}


class MeditationButtonRecyclerAdapter(
    private val items: List<Meditation> = ArrayList(),
    private val actionListener: MeditationOnClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val itemBinding =
            LayoutButtonsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        itemBinding.root.setOnClickListener(this)
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


    class MeditationButtonViewHolder(private val itemBinding: LayoutButtonsListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private val buttonTitle = itemBinding.buttonTitle
        private val buttonTime = itemBinding.buttonTime
        private val buttonImage = itemBinding.backgroundImage

        fun bind(meditationButton: Meditation) {
            itemBinding.root.tag = meditationButton

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

    override fun onClick(p0: View) {
        val meditation = p0.tag as Meditation

        actionListener.onMeditationStart(meditation)
    }


}