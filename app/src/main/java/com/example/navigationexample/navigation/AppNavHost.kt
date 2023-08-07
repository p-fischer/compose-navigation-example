package com.example.navigationexample.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.navigationexample.ui.home.homeNavGraph
import com.example.navigationexample.ui.more.moreNavGraph

@Composable
internal fun AppNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = TabItem.Home.navGraphRoute,
        modifier = Modifier.padding(paddingValues)
    ) {
        homeNavGraph(navController)
        moreNavGraph(navController)
    }
}
