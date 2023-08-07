package com.example.navigationexample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.navigationexample.navigation.AppNavHost
import com.example.navigationexample.navigation.AppNavigationBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { AppNavigationBar(navController) }
    ) { paddingValues ->
        AppNavHost(
            navController = navController,
            paddingValues = paddingValues,
        )
    }
}
