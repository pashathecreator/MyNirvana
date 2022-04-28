package com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mynirvana.presentation.recycler.RecyclerViewType

class SideSpacingItemDecoration(
    private val padding: Int,
    private val recyclerType: RecyclerViewType
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        when (recyclerType) {
            RecyclerViewType.Horizontal ->
                outRect.left = padding
            RecyclerViewType.Vertical ->
                outRect.bottom = padding
        }
    }

}