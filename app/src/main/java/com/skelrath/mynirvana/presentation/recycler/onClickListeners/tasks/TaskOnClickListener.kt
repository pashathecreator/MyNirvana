package com.skelrath.mynirvana.presentation.recycler.onClickListeners.tasks

import com.skelrath.mynirvana.domain.task.model.Task


interface TaskOnClickListener {
    fun onTaskComplete(task: Task)
    fun onTaskRemoved(task: Task)
}