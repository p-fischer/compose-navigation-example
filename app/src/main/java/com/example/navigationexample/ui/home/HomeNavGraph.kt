package com.example.navigationexample.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.navigationexample.navigation.TabItem

private const val HOME_ROUTE = "home"
private const val HOME_DETAIL_ROUTE = "home_detail"

internal fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        route = TabItem.Home.navGraphRoute,
        startDestination = HOME_ROUTE,
    ) {
        composable(HOME_ROUTE) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Button(onClick = { navController.navigateToHomeDetail() }) {
                    Text("Go to Home Detail")
                }
            }
        }
        composable(HOME_DETAIL_ROUTE) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Text("Home Detail")
            }
        }
    }
}

private fun NavHostController.navigateToHomeDetail() {
    navigate(HOME_DETAIL_ROUTE)
}
