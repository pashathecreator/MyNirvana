package com.example.mynirvana.presentation.recycler.adapters.task

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynirvana.R
import com.example.mynirvana.databinding.LayoutTaskListItemBinding
import com.example.mynirvana.domain.task.model.Task
import com.example.mynirvana.presentation.recycler.onClickListeners.tasks.TaskOnClickListener
import com.example.mynirvana.presentation.timeConvertor.TimeWorker

class TaskRecyclerAdapter(
    private val items: List<Task>,
    private val actionListener: TaskOnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            LayoutTaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TaskViewHolder(itemBinding, actionListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TaskViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemCount() = items.size


    class TaskViewHolder(
        private val itemBinding: LayoutTaskListItemBinding,
        private val actionListener: TaskOnClickListener
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(task: Task) {
            with(itemBinding) {
                if (task.isTaskCompleted) {
                    isCompletedImageButton.setImageResource(R.drawable.ic_is_completed_button)
                    nameOfTaskTV.apply {
                        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }
                } else {
                    isCompletedImageButton.setImageResource(R.drawable.ic_is_not_completed_button)
                }

                isCompletedImageButton.setOnClickListener {
                    actionListener.onComplete(task)
                }

                nameOfTaskTV.text = task.name

                timeOfTaskTV.text =
                    TimeWorker.convertSecondsTo24HoursFormat(task.timeWhenTaskStarts)
            }
        }
    }
}