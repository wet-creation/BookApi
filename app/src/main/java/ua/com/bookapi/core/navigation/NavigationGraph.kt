package ua.com.bookapi.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.com.bookapi.core.navigation.graphs.MainGraph
import ua.com.bookapi.features.auth.presentation.AuthScreen
import ua.com.bookapi.features.books.presentation.list.CategoryListRoot


@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Any = MainGraph.Auth,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<MainGraph.Auth> {
            AuthScreen(modifier = Modifier.padding(innerPadding))
        }
        composable<MainGraph.Categories> {
            CategoryListRoot()
        }
        composable<MainGraph.Books> {
            CategoryListRoot() //todo change, when screen will be made
        }
    }
}

