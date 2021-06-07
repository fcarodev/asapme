package com.portfolio.myapp.data.model.project

data class ProjectModel(
        val name:String = "Sample project",
        val description:String ="Sample description",
        val currentSprint:String = "Sample sprint",
        val imgUrl:String = "default",
        val colorBackground:String = "",
        val colorText:String="",
        val progress:String = "0",
        val innerId:String? = "0"
)
