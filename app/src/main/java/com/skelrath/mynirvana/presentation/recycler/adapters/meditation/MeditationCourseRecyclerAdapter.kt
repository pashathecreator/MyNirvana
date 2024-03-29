package com.skelrath.mynirvana.presentation.recycler.adapters.meditation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skelrath.mynirvana.databinding.LayoutMeditationCoursesListItemBinding
import com.skelrath.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.skelrath.mynirvana.presentation.recycler.onClickListeners.meditations.MeditationCourseOnClickListener

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

        return MeditationCourseViewHolder(itemBinding, actionListener)

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
        private val actionListener: MeditationCourseOnClickListener
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(meditationCourse: MeditationCourse) {
            itemBinding.courseTitle.text = meditationCourse.name
            itemBinding.practiceQuantityTV.text = quantityInString(meditationCourse)
            itemBinding.backgroundImage.setImageResource(meditationCourse.imageResourceId)

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
                if (meditation.isMeditationCompleted == true)
                    quantity += 1
            }

            return quantity
        }
    }

}