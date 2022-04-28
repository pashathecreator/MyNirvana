package com.example.mynirvana.presentation.recycler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynirvana.databinding.LayoutMeditationCoursesListItemBinding
import com.example.mynirvana.domain.meditations.model.MeditationCourse

class MeditationCourseRecyclerAdapter(private val items: List<MeditationCourse>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            LayoutMeditationCoursesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MeditationCourseViewHolder(itemBinding)

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
        private val itemBinding: LayoutMeditationCoursesListItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(meditationCourse: MeditationCourse) {
            itemBinding.courseTitle.text = meditationCourse.name
            itemBinding.practiceQuantityTV.text = quantityInString(meditationCourse)
        }

        fun quantityInString(meditationCourse: MeditationCourse): String {
            return "${meditationCourse.completedMeditations.size} из ${meditationCourse.meditationList.size} выполнено"
        }
    }

}