package com.onecode.jan.got.activity

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.onecode.jan.got.detail.HouseDetailScreen
import com.onecode.jan.got.overview.HouseOverviewScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "overview"
    )
    {
        composable(route = "overview") {
            HouseOverviewScreen(
                onClick = {
                    navController.navigate("houseDetail/$it") {
                        popUpTo("overview")
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