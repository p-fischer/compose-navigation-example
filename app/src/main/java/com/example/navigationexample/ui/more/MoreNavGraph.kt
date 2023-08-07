package com.example.navigationexample.ui.more

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

private const val MORE_ROUTE = "more"
private const val MORE_DETAIL_ROUTE = "more_detail"

internal fun NavGraphBuilder.moreNavGraph(navController: NavHostController) {
    navigation(
        route = TabItem.More.navGraphRoute,
        startDestination = MORE_ROUTE,
    ) {
        composable(MORE_ROUTE) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Button(onClick = { navController.navigateToMoreDetail() }) {
                    Text("Go to More Detail")
                }
            }
        }
        composable(MORE_DETAIL_ROUTE) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Text("More Detail")
            }
        }
    }
}

private fun NavHostController.navigateToMoreDetail() {
    navigate(MORE_DETAIL_ROUTE)
}
