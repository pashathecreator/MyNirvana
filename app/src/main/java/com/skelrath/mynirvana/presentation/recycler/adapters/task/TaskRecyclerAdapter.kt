package com.skelrath.mynirvana.presentation.recycler.adapters.task

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.LayoutTaskListItemBinding
import com.skelrath.mynirvana.domain.task.model.Task
import com.skelrath.mynirvana.presentation.recycler.onClickListeners.itemTouchHelper.ItemTouchHelperAdapter
import com.skelrath.mynirvana.presentation.recycler.onClickListeners.tasks.TaskOnClickListener
import com.skelrath.mynirvana.presentation.timeConvertor.TimeWorker
import java.sql.Date

class TaskRecyclerAdapter(
    private val actionListener: TaskOnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    private var items: List<Task> = ArrayList()

    fun submitListOfTasks(items: List<Task>) {
        this.items = items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            LayoutTaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TaskViewHolder(itemBinding, actionListener, parent.context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TaskViewHolder -> {
                if (items.isEmpty()) {
                    holder.bind(Task("Fake Task", 0, Date(0)))
                } else {
                    holder.bind(items[position])
                }
            }
        }
    }

    override fun getItemCount() = items.size


    class TaskViewHolder(
        private val itemBinding: LayoutTaskListItemBinding,
        private val actionListener: TaskOnClickListener,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(task: Task) {
            with(itemBinding) {
                if (task.name == "Fake Task") {
                    userHasZeroTasksTV.visibility = View.VISIBLE
                    userHasZeroTasksTV.text =
                        "Похоже, что на этот день вы еще не запланировали дел"
                } else {
                    userHasZeroTasksTV.visibility = View.GONE

                    if (task.isTaskCompleted) {
                        isCompletedImageButton.background =
                            ContextCompat.getDrawable(context, R.drawable.ic_is_completed_button)
                        nameOfTaskTV.apply {
                            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        }
                    } else {
                        isCompletedImageButton.background =
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_is_not_completed_button
                            )
                        nameOfTaskTV.apply {
                            paintFlags = 0
                        }
                    }

                    itemBinding.isCompletedImageButton.setOnClickListener {
                        actionListener.onTaskComplete(task)
                    }

                    itemBinding.root.setOnClickListener {
                        actionListener.onTaskComplete(task)
                    }

                    nameOfTaskTV.text = task.name

                    timeOfTaskTV.text =
                        TimeWorker.convertSecondsTo24HoursFormat(task.timeWhenTaskStarts)
                }
            }
        }
    }

    override fun onItemSwiped(position: Int) {
        actionListener.onTaskRemoved(items[position])
    }
}