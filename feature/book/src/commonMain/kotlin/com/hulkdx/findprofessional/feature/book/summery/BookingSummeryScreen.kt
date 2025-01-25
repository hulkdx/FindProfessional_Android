package com.hulkdx.findprofessional.feature.book.summery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hulkdx.findprofessional.core.features.book.SelectedTimes
import com.hulkdx.findprofessional.core.features.pro.model.Professional
import com.hulkdx.findprofessional.core.resources.Res
import com.hulkdx.findprofessional.core.resources.checkout
import com.hulkdx.findprofessional.core.resources.connection
import com.hulkdx.findprofessional.core.resources.dateAndTime
import com.hulkdx.findprofessional.core.resources.ic_edit_request
import com.hulkdx.findprofessional.core.resources.total
import com.hulkdx.findprofessional.core.resources.yourRequest
import com.hulkdx.findprofessional.core.resources.yourSkypeId
import com.hulkdx.findprofessional.core.ui.commonui.CUBackButton
import com.hulkdx.findprofessional.core.ui.commonui.CUFilledButton
import com.hulkdx.findprofessional.core.ui.commonui.CUSnackBar
import com.hulkdx.findprofessional.core.ui.theme.AppTheme
import com.hulkdx.findprofessional.core.ui.theme.body1Medium
import com.hulkdx.findprofessional.core.ui.theme.body2
import com.hulkdx.findprofessional.core.ui.theme.h1Medium
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun BookingSummeryScreen(
    professional: Professional,
    times: SelectedTimes,
    viewModel: BookingSummeryViewModel = koinViewModel { parametersOf(professional, times) },
) {
    val error by viewModel.error.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    BookingSummeryScreen(
        uiState = uiState ?: return,
        onCheckoutClicked = viewModel::onCheckoutClicked,
        error = error?.localized(),
        onErrorDismissed = { viewModel.setError(null) }
    )
}

@Composable
fun BookingSummeryScreen(
    uiState: BookingSummeryUiState,
    onCheckoutClicked: () -> Unit,
    error: String?,
    onErrorDismissed: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .systemBarsPadding()
            .testTag("BookingSummeryScreen")
    ) {
        LazyColumn(Modifier.padding(top = 100.dp)) {
            item { YourRequest() }
            item { DateAndTime() }
            items(uiState.times) {
                TimeItem(it)
            }
            item { ConnectionSection(uiState.userSkypeId) }
        }
        CUBackButton(modifier = Modifier.align(Alignment.TopStart))
        Bottom(
            modifier = Modifier.align(Alignment.BottomStart),
            totalPrices = uiState.totalPrices,
            onCheckoutClicked = onCheckoutClicked,
        )
        CUSnackBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            message = error,
            onDismiss = onErrorDismissed
        )
    }
}

@Composable
private fun YourRequest() {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = stringResource(Res.string.yourRequest),
        textAlign = TextAlign.Center,
        style = h1Medium,
    )
}

@Composable
private fun DateAndTime() {
    Text(
        modifier = Modifier
            .padding(
                top = 32.dp,
                start = 26.dp
            ),
        text = stringResource(Res.string.dateAndTime),
        style = body1Medium,
    )
}

@Composable
private fun TimeItem(data: BookingSummeryUiState.Time) {
    Row(
        modifier = Modifier
            .padding(horizontal = 26.dp)
            .padding(top = 8.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = data.day,
            style = body2,
        )
        Spacer(Modifier.weight(1F))
        Text(
            text = data.duration,
            style = body2,
        )
        Spacer(Modifier.weight(1F))
        Text(
            text = data.date,
            style = body2,
        )
        Spacer(Modifier.weight(0.5F))
        Image(painter = painterResource(Res.drawable.ic_edit_request), contentDescription = null)
    }
}

@Composable
private fun ConnectionSection(skypeId: String?) {
    Column {
        Text(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    start = 26.dp
                ),
            text = stringResource(Res.string.connection),
            style = body1Medium,
        )
        SkypeId(skypeId)
    }
}

@Composable
private fun SkypeId(skypeId: String?) {
    Row(
        modifier = Modifier
            .padding(horizontal = 26.dp)
            .padding(top = 8.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(Res.string.yourSkypeId),
            style = body2,
        )
        Spacer(Modifier.weight(2F))
        Text(
            text = skypeId ?: "",
            style = body2,
        )
        Spacer(Modifier.weight(1F))
        Image(
            painter = painterResource(Res.drawable.ic_edit_request),
            contentDescription = null
        )
    }
}

@Composable
private fun Bottom(
    modifier: Modifier = Modifier,
    totalPrices: String,
    onCheckoutClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(1.dp, MaterialTheme.colorScheme.onPrimary)
            .padding(horizontal = 26.dp, vertical = 22.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .padding(horizontal = 4.dp)
        ) {
            Text(
                text = stringResource(Res.string.total),
                style = body1Medium,
            )
            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = totalPrices,
                style = body1Medium,
            )
        }
        CheckoutButton(onCheckoutClicked)
    }
}

@Composable
private fun CheckoutButton(onClick: () -> Unit) {
    CUFilledButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(Res.string.checkout),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
    )
}

@Preview
@Composable
private fun BookingSummeryScreenPreview() {
    AppTheme {
        BookingSummeryScreen(
            error = "",
            onErrorDismissed = {},
            onCheckoutClicked = {},
            uiState = BookingSummeryUiState(
                userSkypeId = "test@gmail.com",
                times = listOf(
                    BookingSummeryUiState.Time(
                        duration = "16:30 - 17:00",
                        date = "1.1.2024",
                        day = "Mon",
                    ),
                    BookingSummeryUiState.Time(
                        duration = "17:30 - 18:00",
                        date = "1.1.2024",
                        day = "Mon",
                    ),
                ),
                totalPrices = "100 €"
            ),
        )
    }
}
