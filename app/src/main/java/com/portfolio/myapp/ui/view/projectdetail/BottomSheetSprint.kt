package com.portfolio.myapp.ui.view.projectdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.sprint.SprintModel
import com.portfolio.myapp.utils.manager.HawkManager
import kotlinx.android.synthetic.main.sheet_sprint_detail.view.*

class BottomSheetSprint( var itemClickListener: SprintClickListener) : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.sheet_sprint_detail, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val sprintModel = HawkManager().getCurrentSprint()

            view.txtNameSprint.text  = sprintModel.name
            view.txtDesctSprint.text = sprintModel.description
            view.txtInitDateSprint.text = sprintModel.dateInit
            view.txtFinishDateSprint.text = sprintModel.dateFinish

            view.btnStartSprint.setOnClickListener { itemClickListener.onStartSprintClickListeenr(sprintModel) }
            view.btnAddTast.setOnClickListener { itemClickListener.onAddTaskClickListener(sprintModel) }
            view.btnDeleteSprint.setOnClickListener { itemClickListener.onDeleteSprintClickListener(sprintModel) }
            view.btEditSprint.setOnClickListener { itemClickListener.onEditSprintClickListener(sprintModel) }
        }

        interface SprintClickListener {
            fun onDeleteSprintClickListener(idSprint:SprintModel)
            fun onStartSprintClickListeenr(sprintModel: SprintModel)
            fun onAddTaskClickListener(sprintModel: SprintModel)
            fun onEditSprintClickListener(sprintModel: SprintModel)
        }
}