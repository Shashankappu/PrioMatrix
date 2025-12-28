package com.example.priomatrix.ui

sealed class Screen(val route: String) {
    object Home : Screen("home")

    object Quadrant : Screen("quadrant/{priorityId}") {
        fun createRoute(priorityId: Int) = "quadrant/$priorityId"
    }
    object TaskDetails : Screen("taskDetails/{taskId}") {
        fun createRoute(taskId: Int) = "taskDetails/$taskId"
    }
}