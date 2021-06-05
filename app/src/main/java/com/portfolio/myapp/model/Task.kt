package com.portfolio.myapp.model

data class Task(
    val id: String = "0",
    val idSprint: String = "1",
    val description: String = "sample description",
    val name: String = "sample name",
    val status:Boolean = true
)
