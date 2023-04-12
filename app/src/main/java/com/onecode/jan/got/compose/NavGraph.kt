package com.onecode.jan.got.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "houseList"
    )
    {
        composable(route = "houseList") {
            HousesOverviewScreen(
                onClick = {
                    navController.navigate("houseDetail/$it") {
                        popUpTo("houseList")
                    }
                })
        }
        composable(
            route = "houseDetail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            HouseDetailScreen(id = it.arguments?.getInt("id"))
        }
    }
}