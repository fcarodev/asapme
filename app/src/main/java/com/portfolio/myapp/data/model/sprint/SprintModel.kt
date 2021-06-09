package com.portfolio.myapp.data.model.sprint

data class SprintModel(
    var innerId:String = "-1",
    var idProject:String = "1",
    var dateFinish:String="10-10-1010",
    var dateInit:String="10-10-1010",
    var description:String="sample description",
    var name:String="sample name",
    var isActive:Boolean = false
)
