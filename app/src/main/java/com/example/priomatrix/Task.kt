package com.example.priomatrix

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority,
    val isCompleted: Boolean
)

// TODO : remove this list
val list = listOf(
    Task(
        1, "Task 1",
        description = "Some Bug in Home Screen",
        priority = PRIORITY_NONE,
        isCompleted = true,
    ),
    Task(
        2, "Task 2",
        description = "Some Feature Implementation",
        priority = PRIORITY_NONE,
        isCompleted = false,
    ),
    Task(
        3, "Task 3",
        description = "Some Bug in Splash Screen",
        priority = PRIORITY_NONE,
        isCompleted = false,
    ),
    Task(
        4, "Task 4",
        description = "Some Bug in Player Screen",
        priority = PRIORITY_NONE,
        isCompleted = false,
    ),
    Task(
        5, "Task 5",
        description = "Some Bug in Video",
        priority = PRIORITY_NONE,
        isCompleted = true,
    ),
)