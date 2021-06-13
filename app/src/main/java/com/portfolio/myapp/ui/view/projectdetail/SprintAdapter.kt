package com.portfolio.myapp.ui.view.projectdetail


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.florent37.viewanimator.ViewAnimator
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.sprint.SprintModel
import kotlinx.android.synthetic.main.item_row_empty_data_sprint.view.*
import kotlinx.android.synthetic.main.item_row_sprint.view.*


class SprintAdapter(var sprintClickListener:SprintClickListener):RecyclerView.Adapter<SprintViewHolder<*>>() {

    private var sprintList = mutableListOf<SprintModel>() //retorna un ArrayList

    fun setListSprint(sprintModels:MutableList<SprintModel>){
        sprintList = sprintModels
    }

    override fun getItemViewType(position: Int): Int {
        if (sprintList[0].innerId == "EmptyDataSprintt") {
            return 0
        }
        if (sprintList[0].innerId == "PlaceholderDataSprint") {
            return 1
        }
        return 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SprintViewHolder<*> {

        return when (viewType) {
            0 -> {
                //Se muestra cuando no hay projectos ([])
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_empty_data_sprint, parent, false)
                SprintEmptyData(view)
            }
            1 -> {
                //Se muestra cuando no se sabe si hay o no projectos (null)
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_placeholder_sprint, parent, false)
                SprintPlaceholder(view)

            }

            2 -> {
                //Se muestra cuando hay project ([data1,data2,data3])
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_sprint, parent, false)
                SprintViewHolderData(view)
            }
            else -> throw IllegalArgumentException("error view type")
        }

    }

    override fun onBindViewHolder(holder: SprintViewHolder<*>, position: Int) {
        when (holder) {
            is SprintAdapter.SprintEmptyData -> holder.bindView(SprintModel("EmptyDataSprintt"), sprintClickListener)
            is SprintAdapter.SprintPlaceholder -> holder.bindView(SprintModel("PlaceholderDataSprint"), sprintClickListener)
            is SprintAdapter.SprintViewHolderData -> holder.bindView(sprintList[position], sprintClickListener)
        }
    }


    override fun getItemCount(): Int {
        if (sprintList[0].innerId == "EmptyDataSprintt") {
            return 1
        }
        if (sprintList[0].innerId == "PlaceholderDataSprint") {
            return 10
        }
        return sprintList.size
    }

    inner class SprintViewHolderData(itemView:View):SprintViewHolder<SprintModel>(itemView) {
        override fun bindView(sprintModel: SprintModel, itemClickListener: SprintAdapter.SprintClickListener) {
            itemView.txtSprintName.text = sprintModel.name
            itemView.txtSprintPercent.text = "44%"
            itemView.progressSprint.progress = 44
            itemView.txtInitDateSprint.text = sprintModel.dateInit
            itemView.txtFinishDateSprint.text = sprintModel.dateFinish
            itemView.cardDetailProject.setOnClickListener{
                sprintClickListener.onSprintClickListener(sprintModel)
            }
        }
    }
    inner class SprintEmptyData(itemView:View):SprintViewHolder<SprintModel>(itemView){
        override fun bindView(item: SprintModel, itemClickListener: SprintClickListener) {
            ViewAnimator
                .animate(itemView.parentSprintEmpty)
                .alpha(0f, 1f)
                .duration(800)
                .andAnimate(itemView.btnAddSprintEmpty)
                .alpha(0f, 1f)
                .duration(800)
                .start()
            itemView.btnAddSprintEmpty.setOnClickListener { itemClickListener.onCreateSprintClickListener() }
        }

    }

    inner class SprintPlaceholder(itemView:View):SprintViewHolder<SprintModel>(itemView){
        override fun bindView(item: SprintModel, itemClickListener: SprintClickListener) {
            val shimer: ShimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container_sprint)
            shimer.startShimmer()
        }

    }

    interface SprintClickListener {
        fun onSprintClickListener(sprintModel: SprintModel)
        fun onCreateSprintClickListener()
    }
}