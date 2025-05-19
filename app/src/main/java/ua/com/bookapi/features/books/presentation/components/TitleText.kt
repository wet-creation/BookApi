package ua.com.bookapi.features.books.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TitleText(modifier: Modifier = Modifier, title: String, text: String) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("$title:", color = MaterialTheme.colorScheme.secondary)
        Text(text, color = MaterialTheme.colorScheme.primary)
    }
}