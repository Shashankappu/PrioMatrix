package com.example.priomatrix

import com.example.priomatrix.ui.Severity
import com.example.priomatrix.ui.TaskStatus

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val owner: String = "",
    val severity: Severity,
    val priority: Priority,
    val createdAt : Long,
    val status: TaskStatus = TaskStatus.PENDING
){
    val isCompleted: Boolean
        get() = status == TaskStatus.COMPLETED
}

// TODO : remove this list
val list = listOf(
    Task(
        1, "Task 1",
        description = "Some Bug in Home Screen",
        priority = PRIORITY_NONE,
        createdAt = 0L,
        owner = "John Doe",
        status = TaskStatus.PENDING,
        severity = Severity.CRITICAL
    ),
    Task(
        2, "Task 2",
        description = "Some Feature Implementation",
        priority = PRIORITY_NONE,
        createdAt = 1L,
        owner = "John Doe",
        status = TaskStatus.COMPLETED,
        severity = Severity.MAJOR
    ),
    Task(
        3, "Task 3",
        description = "Some Bug in Splash Screen",
        priority = PRIORITY_NONE,
        createdAt = 2L,
        owner = "King",
        status = TaskStatus.PENDING,
        severity = Severity.CRITICAL
    ),
    Task(
        4, "Task 4",
        description = "Some Bug in Player Screen",
        priority = PRIORITY_NONE,
        createdAt = 3L,
        owner = "John Doe",
        status = TaskStatus.PENDING,
        severity = Severity.LOW
    ),
    Task(
        5, "Task 5",
        description = "Some Bug in Video",
        priority = PRIORITY_NONE,
        createdAt = 4L,
        owner = "John Doe",
        status = TaskStatus.IN_PROGRESS,
        severity = Severity.MODERATE
    ),

    Task(
        6, "Task 6",
        description = "Some Bug in Home Screen",
        priority = PRIORITY_NONE,
        createdAt = 5L,
        owner = "King",
        status = TaskStatus.IN_PROGRESS,
        severity = Severity.MODERATE
    ),
    Task(
        7, "Task 7",
        description = "Some Feature Implementation",
        priority = PRIORITY_NONE,
        createdAt = 6L,
        owner = "James",
        status = TaskStatus.IN_PROGRESS,
        severity = Severity.MAJOR
    ),
    Task(
        8, "Task 8",
        description = "Some Bug in Splash Screen",
        priority = PRIORITY_NONE,
        createdAt = 7L,
        owner = "John Doe",
        status = TaskStatus.PENDING,
        severity = Severity.MODERATE
    ),
    Task(
        9, "Task 9",
        description = "Some Bug in Player Screen",
        priority = PRIORITY_NONE,
        createdAt = 8L,
        owner = "James",
        status = TaskStatus.PENDING,
        severity = Severity.MODERATE
    ),
    Task(
        10, "Task 10",
        description = "Some Bug in Video",
        priority = PRIORITY_NONE,
        createdAt = 9L,
        owner = "James",
        status = TaskStatus.COMPLETED,
        severity = Severity.LOW
    ),
)