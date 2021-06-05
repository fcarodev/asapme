package com.portfolio.myapp.model

data class Project(
        val id:String ="0",
        val name:String = "Sample project",
        val description:String ="Sample description",
        val currentSprint:String = "Sample sprint",
        val imgUrl:String = "default",
        val colorBackground:String = "",
        val colorText:String="",
        val progress:Int = 0
)
