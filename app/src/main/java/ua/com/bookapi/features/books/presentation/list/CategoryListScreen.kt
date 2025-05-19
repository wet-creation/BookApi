package ua.com.bookapi.features.books.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import org.koin.androidx.compose.koinViewModel
import ua.com.bookapi.R
import ua.com.bookapi.core.ui.theme.BookApiTheme
import ua.com.bookapi.core.utils.emptyUiText

@Composable
fun CategoryListRoot(
    innerPadding: PaddingValues,
    viewModel: CategoryListViewModel = koinViewModel(),
    navigateToBooks: (Int, String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CategoryListScreen(
        innerPadding = innerPadding,
        state = state,
        onAction = {
            viewModel.onAction(it)
            when (it) {
                is CategoryListAction.NavigateToBooks -> navigateToBooks(it.categoryId, it.categoryName)
                else -> Unit
            }
        }
    )
}

@Composable
fun CategoryListScreen(
    innerPadding: PaddingValues,
    state: CategoryListState,
    onAction: (CategoryListAction) -> Unit,
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
                onAction(CategoryListAction.DismissDialog)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAction(CategoryListAction.DismissDialog)
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
            items(state.categories) {
                Card(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .clickable {
                            onAction(CategoryListAction.NavigateToBooks(it.id, it.name))
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)

                    ) {
                        Text(it.name)
                        Text(it.publishedDate)

                    }
                }
            }
        }
}

@Preview
@Composable
private fun Preview() {
    BookApiTheme {
        CategoryListScreen(
            innerPadding = PaddingValues(),
            state = CategoryListState(),
            onAction = {}
        )
    }
}