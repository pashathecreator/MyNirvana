package com.example.mynirvana.presentation.endSoundsRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynirvana.databinding.LayoutSoundListItemBinding
import com.example.mynirvana.domain.endSounds.model.EndSound

class EndSoundsRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<EndSound> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = LayoutSoundListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return EndSoundViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EndSoundViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(list: List<EndSound>) {
        items = list
    }

    class EndSoundViewHolder(itemBinding: LayoutSoundListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val name = itemBinding.name
        private val image = itemBinding.iconImage

        fun bind(endSound: EndSound) {
            name.text = endSound.name
            image.setImageResource(endSound.icon)
        }


    }
}
