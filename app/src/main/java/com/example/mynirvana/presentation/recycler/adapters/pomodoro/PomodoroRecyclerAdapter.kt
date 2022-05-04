package com.example.mynirvana.presentation.recycler.adapters.pomodoro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynirvana.databinding.LayoutPomodoroListItemBinding
import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.presentation.timeConvertor.TimeConvertor

class PomodoroRecyclerAdapter(private val items: List<Pomodoro>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = LayoutPomodoroListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PomodoroViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PomodoroViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int =
        items.size


    class PomodoroViewHolder(private val itemBinding: LayoutPomodoroListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(pomodoro: Pomodoro) {
            with(itemBinding) {
                pomodoroTimerNameTV.text = pomodoro.name
                quanitityOfCirclesTV.text = "${pomodoro.quantityOfCircles} кругов"
                workRelaxTimeTV.text =
                    "${TimeConvertor.convertTimeFromSecondsToMinutesFormatWithoutTimeWord(pomodoro.workTimeInSeconds)}/${
                        TimeConvertor.convertTimeFromSecondsToMinutesFormatWithoutTimeWord(
                            pomodoro.relaxTimeInSeconds
                        )
                    } минут"
            }
        }
    }
}