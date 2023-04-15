package com.onecode.jan.got.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.onecode.jan.got.detail.HouseDetailScreen
import com.onecode.jan.got.overview.HouseOverviewScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Destination.HouseOverview.routePattern
    )
    {
        composable(route = Destination.HouseOverview.routePattern) {
            HouseOverviewScreen(
                onClick = {
                    navController.navigate(Destination.HouseDetails.createRoute(it)) {
                        popUpTo(Destination.HouseOverview.routePattern)
                    }
                })
        }
        composable(
            route = Destination.HouseDetails.routePattern,
            arguments = Destination.HouseDetails.arguments
        ) {
            HouseDetailScreen()
        }
    }
}