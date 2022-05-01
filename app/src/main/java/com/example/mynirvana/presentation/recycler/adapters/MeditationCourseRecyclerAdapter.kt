package com.example.mynirvana.presentation.recycler.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mynirvana.databinding.LayoutMeditationCoursesListItemBinding
import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.example.mynirvana.presentation.recycler.onClickListeners.MeditationCourseOnClickListener

class MeditationCourseRecyclerAdapter(
    private val items: List<MeditationCourse>,
    private val actionListener: MeditationCourseOnClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            LayoutMeditationCoursesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MeditationCourseViewHolder(itemBinding, parent.context, actionListener)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MeditationCourseViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MeditationCourseViewHolder(
        private val itemBinding: LayoutMeditationCoursesListItemBinding,
        private val context: Context,
        private val actionListener: MeditationCourseOnClickListener
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(meditationCourse: MeditationCourse) {
            itemBinding.courseTitle.text = meditationCourse.name
            itemBinding.practiceQuantityTV.text = quantityInString(meditationCourse)
            itemBinding.backgroundImage.background =
                ContextCompat.getDrawable(context, meditationCourse.imageResourceId)

            itemBinding.root.setOnClickListener {
                actionListener.onMeditationCourseStart(meditationCourse)
            }
        }

        private fun quantityInString(meditationCourse: MeditationCourse): String {
            return "${quantityOfCompletedMeditations(meditationCourse)} из ${meditationCourse.meditationList.size} выполнено"
        }

        private fun quantityOfCompletedMeditations(meditationCourse: MeditationCourse): Int {
            var quantity = 0
            for (meditation in meditationCourse.meditationList) {
                if (meditation.isMeditationCompleted)
                    quantity += 1
            }

            return quantity
        }
    }

}