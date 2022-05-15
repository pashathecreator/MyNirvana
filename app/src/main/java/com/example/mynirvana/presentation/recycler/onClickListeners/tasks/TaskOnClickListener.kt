package com.example.mynirvana.presentation.recycler.onClickListeners.tasks

import com.example.mynirvana.domain.task.model.Task


interface TaskOnClickListener {
    fun onTaskComplete(task: Task)
    fun onTaskRemoved(position: Int)
}