package com.portfolio.myapp.ui.view.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.sprint.SprintModel
import com.portfolio.myapp.data.model.task.TaskModel
import com.portfolio.myapp.utils.manager.HawkManager
import kotlinx.android.synthetic.main.sheet_sprint_detail.view.*
import kotlinx.android.synthetic.main.sheet_sprint_detail.view.btEditSprint
import kotlinx.android.synthetic.main.sheet_sprint_detail.view.btnDeleteSprint
import kotlinx.android.synthetic.main.sheet_sprint_detail.view.txtNameSprint
import kotlinx.android.synthetic.main.sheet_task_detail.view.*

class BottomSheetTask( var itemClickListener: TaskClickListener) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sheet_task_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val taskModel = HawkManager().getCurrentTask()
        view.txtNameTask.text  = taskModel.name
        view.txtDesctTask.text = taskModel.description
        view.btEditTask.setOnClickListener { itemClickListener.onEditTaskClickListener(taskModel) }
        view.btnDeleteTask.setOnClickListener { itemClickListener.onDeleteTaskClickListener(taskModel.innerId) }
    }

    interface TaskClickListener {
        fun onDeleteTaskClickListener(idTask:String)
        fun onEditTaskClickListener(taskModel: TaskModel)
    }
}