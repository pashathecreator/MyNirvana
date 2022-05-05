package com.example.mynirvana.presentation.recycler.adapters.meditation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynirvana.databinding.LayoutMeditationsListItemBinding
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.presentation.recycler.onClickListeners.meditations.MeditationOnClickListener
import com.example.mynirvana.presentation.timeConvertor.TimeConvertor


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
            LayoutMeditationsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MeditationViewHolder(itemBinding, actionListener)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is MeditationViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class MeditationViewHolder(
        private val itemBinding: LayoutMeditationsListItemBinding,
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
            buttonTime.text =
                TimeConvertor.convertTimeFromSecondsToMinutesFormat(meditation.time)

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
                    actionListener.onMeditationDelete(meditation)
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
