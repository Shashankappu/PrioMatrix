package com.example.priomatrix.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.priomatrix.HomeScreen
import com.example.priomatrix.TaskViewModel

@Composable
fun  AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){

    val taskViewModel: TaskViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ){
        composable(Screen.Home.route) {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                taskViewModel = taskViewModel,
                onQuadrantClick = { priority ->
                    navController.navigate(
                        Screen.Quadrant.createRoute(priority.id)
                    )
                },
            )
        }

        composable(
            route = Screen.Quadrant.route,
            arguments = listOf(
                navArgument("priorityId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val priorityId = backStackEntry.arguments!!.getInt("priorityId")

            QuadrantTasksScreen(
                priorityId = priorityId,
                taskViewModel = taskViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }

}