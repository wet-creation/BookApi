package ua.com.bookapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.android.ext.android.inject
import org.koin.compose.KoinContext
import ua.com.bookapi.core.Config
import ua.com.bookapi.core.navigation.NavigationRoot
import ua.com.bookapi.core.navigation.graphs.MainGraph
import ua.com.bookapi.core.navigation.routeClass
import ua.com.bookapi.core.ui.theme.BookApiTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel by inject<MainViewModel>()
        installSplashScreen().apply {
            this.setKeepOnScreenCondition { viewModel.state == null }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initConfig()
        setContent {
            var topBarTitle by remember {
                mutableStateOf<String?>(null)
            }
            val navController = rememberNavController()
            val backStack by navController.currentBackStackEntryAsState()
            val showNavBack = remember {
                derivedStateOf {
                    backStack?.destination?.routeClass() != MainGraph.Categories::class
                }
            }
            val start = if (viewModel.state == false) {
                MainGraph.Auth
            } else MainGraph.Categories

            KoinContext {
                BookApiTheme {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            topBarTitle?.let {
                                CenterAlignedTopAppBar(
                                    navigationIcon = {
                                        if (showNavBack.value)
                                            IconButton(
                                                onClick = {
                                                  navController.navigateUp()
                                                }
                                            ) {
                                                Icon(
                                                    contentDescription = stringResource(R.string.navigate_back),
                                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack
                                                )
                                            }
                                    },
                                    title = { Text(it, overflow = TextOverflow.Ellipsis, maxLines = 1) }
                                )
                            }
                        }) { innerPadding ->
                        NavigationRoot(
                            navController,
                            start,
                            innerPadding
                        ) {
                            topBarTitle = it
                        }
                    }
                }
            }
        }
    }

    private fun initConfig() {
        Config.webClientId = BuildConfig.WEB_CLIENT_ID
        Config.token = BuildConfig.BOOK_API_TOKEN
    }
}

