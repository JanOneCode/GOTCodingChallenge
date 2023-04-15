package com.onecode.jan.got.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

const val HOUSE_ID = "houseId"

sealed class Destination(
    val name: String,
) {
    abstract val routePattern: String
    abstract val arguments: List<NamedNavArgument>

    object HouseOverview : Destination("overview") {
        override val routePattern: String = name
        override val arguments: List<NamedNavArgument> = emptyList()
    }

    object HouseDetails : Destination("houseDetail") {
        override val routePattern: String = "$name/{$HOUSE_ID}"
        override val arguments: List<NamedNavArgument> =
            listOf(navArgument(HOUSE_ID) { type = NavType.IntType })

        fun createRoute(id: Int): String = "$name/$id"
    }
}