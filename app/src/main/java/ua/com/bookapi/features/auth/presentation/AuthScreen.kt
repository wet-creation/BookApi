package ua.com.bookapi.features.auth.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ua.com.bookapi.R
import ua.com.bookapi.core.Config
import ua.com.bookapi.ui.theme.Purple40

@Composable
fun AuthScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<AuthViewModel>()
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AuthenticationButton {
            viewModel.onSignInWithGoogle(it)
        }
    }
}

@Composable
fun AuthenticationButton(onRequestResult: (Credential) -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = { coroutineScope.launch { launchCredManButtonUI(context, onRequestResult) } },
        colors = ButtonDefaults.buttonColors(containerColor = Purple40),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.google_logo),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(24.dp),
            contentDescription = stringResource(R.string.google_logo_description)
        )

        Text(
            color = Color.White,
            text = stringResource(R.string.login_with_google),
            fontSize = 16.sp,
            modifier = Modifier.padding(0.dp, 6.dp)
        )
    }
}

private suspend fun launchCredManButtonUI(
    context: Context,
    onRequestResult: (Credential) -> Unit
) {
    try {
        val signInWithGoogleOption = GetSignInWithGoogleOption
            .Builder(serverClientId = Config.webClientId)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        val result = CredentialManager.create(context).getCredential(
            request = request,
            context = context
        )

        onRequestResult(result.credential)
    } catch (e: NoCredentialException) {

    } catch (e: GetCredentialException) {
    }
}