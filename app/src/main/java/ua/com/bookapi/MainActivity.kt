package ua.com.bookapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import org.koin.android.ext.android.inject
import org.koin.compose.KoinContext
import ua.com.bookapi.core.Config
import ua.com.bookapi.core.navigation.NavigationRoot
import ua.com.bookapi.core.navigation.graphs.MainGraph
import ua.com.bookapi.core.ui.theme.BookApiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initConfig()
        val viewModel by inject<MainViewModel>()
        installSplashScreen().apply {
            this.setKeepOnScreenCondition { viewModel.state == null }
        }

        setContent {
            val navController = rememberNavController()
            val start = if (viewModel.state == true) {
                MainGraph.Categories
            } else MainGraph.Auth
            KoinContext {
                BookApiTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavigationRoot(
                            navController,
                            start,
                            innerPadding
                        )
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

