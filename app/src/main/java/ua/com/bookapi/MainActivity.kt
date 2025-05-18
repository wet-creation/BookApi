package ua.com.bookapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.android.ext.android.inject
import org.koin.compose.KoinContext
import ua.com.bookapi.core.Config
import ua.com.bookapi.features.auth.presentation.AuthScreen
import ua.com.bookapi.features.books.list.CategoryListRoot
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
            KoinContext {
                BookApiTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        if (viewModel.state == true)
                            CategoryListRoot()
                        else AuthScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }

    private fun initConfig() {
        Config.webClientId = BuildConfig.WEB_CLIENT_ID
    }
}

