package com.example.navigationexample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object HomeDetail : Screen("home_detail")
    object More : Screen("more")
    object MoreDetail : Screen("more_detail")
}

enum class TopLevelDestination(
    val title: String,
    val navGraphRoute: String,
) {
    HOME("Home", "home_graph"),
    MORE("More", "more_graph"),
}

val bottomBarItems = listOf(TopLevelDestination.HOME, TopLevelDestination.MORE)

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentTopLevelDestination by navController.currentTopLevelDestinationAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                bottomBarItems.forEach { item ->
                    val isTabAlreadySelected = item == currentTopLevelDestination
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(item.title) },
                        selected = isTabAlreadySelected,
                        onClick = {
                            navController.navigate(item.navGraphRoute) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = !isTabAlreadySelected
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = !isTabAlreadySelected
                            }
                        }
                    )
                }
            }

        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = TopLevelDestination.HOME.navGraphRoute,
            modifier = Modifier.padding(paddingValues)
        ) {
            navigation(
                route = TopLevelDestination.HOME.navGraphRoute,
                startDestination = Screen.Home.route,
            ) {
                composable(Screen.Home.route) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Button(onClick = { navController.navigate(Screen.HomeDetail.route) }) {
                            Text("Go to Home Detail")
                        }
                    }
                }
                composable(Screen.HomeDetail.route) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text("Home Detail")
                    }
                }
            }
            navigation(
                route = TopLevelDestination.MORE.navGraphRoute,
                startDestination = Screen.More.route,
            ) {
                composable(Screen.More.route) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Button(onClick = { navController.navigate(Screen.MoreDetail.route) }) {
                            Text("Go to More Detail")
                        }
                    }
                }
                composable(Screen.MoreDetail.route) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text("More Detail")
                    }
                }
            }
        }
    }
}

/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 */
@Composable
fun NavController.currentTopLevelDestinationAsState(): State<TopLevelDestination> {
    val selectedItem = remember { mutableStateOf(TopLevelDestination.HOME) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == TopLevelDestination.MORE.navGraphRoute } -> {
                    selectedItem.value = TopLevelDestination.MORE
                }

                // TopLevelDestination.HOME is the start destination and, therefore, part of any stack
                else -> {
                    selectedItem.value = TopLevelDestination.HOME
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}
