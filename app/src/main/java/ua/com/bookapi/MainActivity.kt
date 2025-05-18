package ua.com.bookapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import org.koin.compose.KoinContext
import ua.com.bookapi.core.Config
import ua.com.bookapi.features.auth.presentation.AuthScreen
import ua.com.bookapi.ui.theme.BookApiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initConfig()
        setContent {
            KoinContext {
                BookApiTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        AuthScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }

    private fun initConfig() {
        Config.webClientId = BuildConfig.WEB_CLIENT_ID
    }
}

