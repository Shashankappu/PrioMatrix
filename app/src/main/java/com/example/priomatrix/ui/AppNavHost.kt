package com.example.priomatrix.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.priomatrix.HomeScreen

@Composable
fun  AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ){
        composable(Screen.Home.route) {
            HomeScreen(
                modifier = modifier,
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
                onBack = { navController.popBackStack() }
            )
        }
    }

}