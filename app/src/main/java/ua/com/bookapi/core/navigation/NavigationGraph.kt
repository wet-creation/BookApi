package ua.com.bookapi.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ua.com.bookapi.core.navigation.graphs.MainGraph
import ua.com.bookapi.features.auth.presentation.AuthScreen
import ua.com.bookapi.features.books.presentation.detail.CategoryDetailRoot
import ua.com.bookapi.features.books.presentation.detail.CategoryDetailViewModel
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
            CategoryListRoot(
                innerPadding = innerPadding,
                navigateToBooks = {
                    navController.navigate(MainGraph.Books(it))
                }
            )
        }
        composable<MainGraph.Books> {
            val id = it.toRoute<MainGraph.Books>().id
            val viewModel = koinViewModel<CategoryDetailViewModel>(parameters = { parametersOf(id) })
            CategoryDetailRoot(
                innerPadding = innerPadding,
                viewModel = viewModel
            )
        }
    }
}

