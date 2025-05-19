package ua.com.bookapi.core.navigation

import android.view.ViewGroup
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kevinnzou.web.WebView
import com.kevinnzou.web.rememberWebViewState
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
    startDestination: Any,
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
            val viewModel =
                koinViewModel<CategoryDetailViewModel>(parameters = { parametersOf(id) })
            CategoryDetailRoot(
                innerPadding = innerPadding,
                viewModel = viewModel
            ) {
                navController.navigate(MainGraph.WebView(it))
            }
        }
        composable<MainGraph.WebView> {
            val url = it.toRoute<MainGraph.WebView>().url
            val state =
                rememberWebViewState(url)
            WebView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    )
                    .imePadding(),
                state = state,
                onCreated = { webView ->
                    webView.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    webView.settings.useWideViewPort = true
                    webView.settings.loadWithOverviewMode = true
                },
            )

        }
    }
}

