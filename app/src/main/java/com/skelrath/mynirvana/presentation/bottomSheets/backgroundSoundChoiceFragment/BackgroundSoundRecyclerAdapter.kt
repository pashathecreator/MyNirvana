package com.skelrath.mynirvana.presentation.bottomSheets.backgroundSoundChoiceFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skelrath.mynirvana.databinding.LayoutSoundListItemBinding
import com.skelrath.mynirvana.domain.backgroundSounds.model.BackgroundSound

class BackgroundSoundRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<BackgroundSound> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = LayoutSoundListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BackgroundSoundViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BackgroundSoundViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(list: List<BackgroundSound>) {
        items = list
    }

    class BackgroundSoundViewHolder(itemBinding: LayoutSoundListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val name = itemBinding.name
        private val image = itemBinding.iconImage

        fun bind(backgroundSound: BackgroundSound) {
            name.text = backgroundSound.name
            image.setImageResource(backgroundSound.icon)
        }


    }
}