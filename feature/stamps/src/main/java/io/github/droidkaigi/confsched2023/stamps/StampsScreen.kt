package io.github.droidkaigi.confsched2023.stamps

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.stamps.section.StampsSheet
import io.github.droidkaigi.confsched2023.stamps.section.StampsSheetUiState
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val stampsScreenRoute = "stamps"
fun NavGraphBuilder.nestedStampsScreen(
    onStampsClick: () -> Unit,
) {
    composable(stampsScreenRoute) {
        StampsScreen(
            onStampsClick = onStampsClick,
        )
    }
}

fun NavController.navigateStampsScreen() {
    navigate(stampsScreenRoute) {
        launchSingleTop = true
        restoreState = true
    }
}

const val StampsScreenTestTag = "StampsScreen"

@Composable
fun StampsScreen(
    onStampsClick: () -> Unit,
    viewModel: StampsScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    StampsScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onStampsClick = onStampsClick,
    )
}

data class StampsScreenUiState(
    val contentUiState: StampsSheetUiState,
)

@Composable
private fun StampsScreen(
    uiState: StampsScreenUiState,
    snackbarHostState: SnackbarHostState,
    onStampsClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(StampsScreenTestTag),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            val layoutDirection = LocalLayoutDirection.current

            StampsSheet(
                modifier = Modifier.padding(
                    PaddingValues(
                        top = padding.calculateTopPadding() + 20.dp,
                        start = padding.calculateStartPadding(layoutDirection) + 16.dp,
                        end = padding.calculateEndPadding(layoutDirection) + 16.dp,
                    ),
                ),
                onStampsClick = onStampsClick,
                uiState = uiState.contentUiState,
            )
        },
    )
}
