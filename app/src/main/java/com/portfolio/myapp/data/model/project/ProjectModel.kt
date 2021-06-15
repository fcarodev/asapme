package com.portfolio.myapp.data.model.project

data class ProjectModel(
    var name:String = "Sample project",
    var description:String ="Sample description",
    var currentSprint:String = "-1",
    var imgUrl:String = "default",
    var colorBackground:String = "",
    var colorText:String="",
    var progress:String = "0",
    var innerId:String? = "0",
    var userId:String? = "0"
)
