package com.example.priomatrix

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority,
    val createdAt : Long,
    val isCompleted: Boolean
)

// TODO : remove this list
val list = listOf(
    Task(
        1, "Task 1",
        description = "Some Bug in Home Screen",
        priority = PRIORITY_NONE,
        isCompleted = true,
        createdAt = 0L
    ),
    Task(
        2, "Task 2",
        description = "Some Feature Implementation",
        priority = PRIORITY_NONE,
        isCompleted = false,
        createdAt = 1L
    ),
    Task(
        3, "Task 3",
        description = "Some Bug in Splash Screen",
        priority = PRIORITY_NONE,
        isCompleted = false,
        createdAt = 2L
    ),
    Task(
        4, "Task 4",
        description = "Some Bug in Player Screen",
        priority = PRIORITY_NONE,
        isCompleted = false,
        createdAt = 3L
    ),
    Task(
        5, "Task 5",
        description = "Some Bug in Video",
        priority = PRIORITY_NONE,
        isCompleted = true,
        createdAt = 4L
    ),

    Task(
        6, "Task 6",
        description = "Some Bug in Home Screen",
        priority = PRIORITY_NONE,
        isCompleted = true,
        createdAt = 5L
    ),
    Task(
        7, "Task 7",
        description = "Some Feature Implementation",
        priority = PRIORITY_NONE,
        isCompleted = false,
        createdAt = 6L
    ),
    Task(
        8, "Task 8",
        description = "Some Bug in Splash Screen",
        priority = PRIORITY_NONE,
        isCompleted = false,
        createdAt = 7L
    ),
    Task(
        9, "Task 9",
        description = "Some Bug in Player Screen",
        priority = PRIORITY_NONE,
        isCompleted = false,
        createdAt = 8L
    ),
    Task(
        10, "Task 10",
        description = "Some Bug in Video",
        priority = PRIORITY_NONE,
        isCompleted = true,
        createdAt = 9L
    ),
)