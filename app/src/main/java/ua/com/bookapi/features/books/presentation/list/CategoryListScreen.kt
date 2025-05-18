package ua.com.bookapi.features.books.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ua.com.bookapi.core.ui.theme.BookApiTheme

@Composable
fun CategoryListRoot(
    viewModel: CategoryListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CategoryListScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun CategoryListScreen(
    state: CategoryListState,
    onAction: (CategoryListAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    BookApiTheme {
        CategoryListScreen(
            state = CategoryListState(),
            onAction = {}
        )
    }
}