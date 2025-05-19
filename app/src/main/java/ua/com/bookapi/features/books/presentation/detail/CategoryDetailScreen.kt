package ua.com.bookapi.features.books.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import ua.com.bookapi.R
import ua.com.bookapi.core.ui.theme.BookApiTheme
import ua.com.bookapi.core.utils.emptyUiText
import ua.com.bookapi.features.books.presentation.components.TitleText

@Composable
fun CategoryDetailRoot(
    innerPadding: PaddingValues,
    viewModel: CategoryDetailViewModel = viewModel(),
    navigateToShop: (String, String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CategoryDetailScreen(
        innerPadding = innerPadding,
        state = state,
        onAction = {
            viewModel.onAction(it)
            when (it) {
                is CategoryDetailAction.NavigateToBrowser -> navigateToShop(it.url, it.name)
                else -> Unit
            }
        }
    )
}

@Composable
fun CategoryDetailScreen(
    innerPadding: PaddingValues,
    state: CategoryDetailState,
    onAction: (CategoryDetailAction) -> Unit,
) {
    if (state.error != emptyUiText)
        AlertDialog(
            title = {
                Text(text = stringResource(R.string.error_title))
            },
            text = {
                Text(text = state.error.asString())
            },
            onDismissRequest = {
                onAction(CategoryDetailAction.DismissDialog)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAction(CategoryDetailAction.DismissDialog)
                    }
                ) {
                    Text(stringResource(R.string.confirm))
                }
            }
        )
    if (state.isLoading)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    else
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(state.books) {
                Card(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .clickable {
                            onAction(CategoryDetailAction.NavigateToBrowser(it.productUrl, it.name))
                        }
                ) {

                    Column(
                        modifier = Modifier
                            .padding(10.dp)

                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(it.rank.toString())
                            Image(
                                contentDescription = stringResource(R.string.book_image, it.name),
                                painter = rememberAsyncImagePainter(it.bookImage),
                                modifier = Modifier.size(100.dp, 125.dp)
                            )
                            Column {
                                Text(it.name, color = MaterialTheme.colorScheme.primary)
                                Text(it.description, color = MaterialTheme.colorScheme.tertiary)
                                TitleText(
                                    title = stringResource(R.string.author),
                                    text = it.author
                                )
                                TitleText(
                                    title = stringResource(R.string.publisher),
                                    text = it.publisher
                                )
                            }
                        }


                    }
                }
            }
        }
}


@Preview
@Composable
private fun Preview() {
    BookApiTheme {
        CategoryDetailScreen(
            state = CategoryDetailState(),
            onAction = {},
            innerPadding = PaddingValues()
        )
    }
}