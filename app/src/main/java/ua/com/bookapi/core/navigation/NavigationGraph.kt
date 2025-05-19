package ua.com.bookapi.core.navigation

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kevinnzou.web.WebView
import com.kevinnzou.web.rememberWebViewState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ua.com.bookapi.R
import ua.com.bookapi.core.navigation.graphs.MainGraph
import ua.com.bookapi.features.auth.presentation.AuthScreen
import ua.com.bookapi.features.books.presentation.detail.CategoryDetailRoot
import ua.com.bookapi.features.books.presentation.detail.CategoryDetailViewModel
import ua.com.bookapi.features.books.presentation.list.CategoryListRoot
import kotlin.reflect.KClass


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Any,
    innerPadding: PaddingValues,
    topBarName: (String?) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<MainGraph.Auth> {
            topBarName(null)
            AuthScreen(modifier = Modifier.padding(innerPadding))
        }
        composable<MainGraph.Categories> {
            topBarName(stringResource(R.string.category_text))
            CategoryListRoot(
                innerPadding = innerPadding,
                navigateToBooks = {id, name ->
                    navController.navigate(MainGraph.Books(id, name))
                }
            )
        }
        composable<MainGraph.Books> {
            val id = it.toRoute<MainGraph.Books>().id
            val name = it.toRoute<MainGraph.Books>().name
            topBarName(name)
            val viewModel =
                koinViewModel<CategoryDetailViewModel>(parameters = { parametersOf(id) })
            CategoryDetailRoot(
                innerPadding = innerPadding,
                viewModel = viewModel
            ) { url, name ->
                navController.navigate(MainGraph.WebView(url, name))
            }
        }
        composable<MainGraph.WebView> {
            val url = it.toRoute<MainGraph.WebView>().url
            val name = it.toRoute<MainGraph.WebView>().name
            topBarName(name)
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
                    webView.settings.javaScriptEnabled = true
                    webView.settings.loadWithOverviewMode = true
                },
            )

        }
    }
}

fun NavDestination.routeClass(): KClass<*>? {
    return this
        .route
        ?.split("/")
        ?.first()
        ?.let { className ->
            generateSequence(className, ::replaceDotByDollar).mapNotNull(::tryParseClass)
                .firstOrNull()
        }
}

private fun tryParseClass(className: String): KClass<*>? {
    return runCatching { Class.forName(className).kotlin }.getOrNull()
}
private fun replaceDotByDollar(input: String): String? {
    val index = input.lastIndexOf('.')
    return if (index != -1) {
        String(input.toCharArray().apply { set(index, '$') })
    } else null
}


