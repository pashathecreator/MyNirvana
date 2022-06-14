package com.skelrath.mynirvana.presentation.recycler.adapters.habit

import android.content.Context
import android.graphics.Paint
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.LayoutTaskListItemBinding
import com.skelrath.mynirvana.domain.habit.model.Habit
import com.skelrath.mynirvana.presentation.recycler.onClickListeners.habits.HabitOnClickListener
import com.skelrath.mynirvana.presentation.recycler.onClickListeners.itemTouchHelper.ItemTouchHelperAdapter
import java.sql.Date

class HabitRecyclerAdapter(
    private val actionListener: HabitOnClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    private var items: List<Habit> = ArrayList()

    fun submitListOfHabits(items: List<Habit>) {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            LayoutTaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(itemBinding, actionListener, parent.context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HabitViewHolder -> {
                if (items.isEmpty()) {
                    holder.bind(Habit("Fake Habit", habitDate = Date(0L)))
                } else {
                    holder.bind(items[position])
                }
            }
        }
    }

    override fun getItemCount() = items.size


    class HabitViewHolder(
        private val itemBinding: LayoutTaskListItemBinding,
        private val actionListener: HabitOnClickListener,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(habit: Habit) {
            with(itemBinding) {
                if (habit.name == "Fake Habit") {
                    userHasZeroTasksTV.visibility = View.VISIBLE
                    userHasZeroTasksTV.text = "Похоже, что вы еще не создали ни одной привычки"
                } else {
                    userHasZeroTasksTV.visibility = View.GONE

                    if (habit.isHabitCompleted == true) {
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
                        actionListener.onHabitComplete(habit)
                    }

                    itemBinding.root.setOnClickListener {
                        actionListener.onHabitComplete(habit)
                    }

                    nameOfTaskTV.text = habit.name

                    timeOfTaskTV.text = ""
                }
            }
        }
    }

    override fun onItemSwiped(position: Int) {
        actionListener.onHabitRemoved(items[position])
    }


}