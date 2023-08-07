package com.example.navigationexample.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

internal enum class TabItem(
    val title: String,
    val navGraphRoute: String,
) {
    Home("Home", "home_graph"),
    More("More", "more_graph"),
}

private val bottomBarItems = listOf(TabItem.Home, TabItem.More)

@Composable
internal fun AppNavigationBar(
    navController: NavHostController
) {
    val currentTopLevelDestination by navController.currentTabItemAsState()

    NavigationBar {
        bottomBarItems.forEach { item ->
            val isTabSelected = item == currentTopLevelDestination
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(item.title) },
                selected = isTabSelected,
                onClick = {
                    navController.navigateToTabItem(
                        item = item,
                        restoreTabStack = !isTabSelected,
                    )
                }
            )
        }
    }
}

/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 */
@Composable
private fun NavController.currentTabItemAsState(): State<TabItem> {
    val selectedItem = remember { mutableStateOf(TabItem.Home) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == TabItem.More.navGraphRoute } -> {
                    selectedItem.value = TabItem.More
                }

                // TopLevelDestination.HOME is the start destination and, therefore, part of any stack
                else -> {
                    selectedItem.value = TabItem.Home
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

private fun NavHostController.navigateToTabItem(
    item: TabItem,
    restoreTabStack: Boolean,
) {
    navigate(item.navGraphRoute) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = restoreTabStack
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = restoreTabStack
    }
}
