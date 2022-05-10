package com.example.mynirvana.presentation.recycler.onClickListeners.tasks

import com.example.mynirvana.domain.task.model.Task


interface TaskOnClickListener {
    fun onComplete(task: Task)
}