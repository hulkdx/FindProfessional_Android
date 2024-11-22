package com.hulkdx.findprofessional.feature.pro.profile.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.hulkdx.findprofessional.core.commonui.CUFilledButton
import com.hulkdx.findprofessional.core.commonui.CUTextField
import com.hulkdx.findprofessional.core.commonui.navbar.AppNavigationBarDimens
import com.hulkdx.findprofessional.core.commonui.navbar.ProAppNavBarContainer
import com.hulkdx.findprofessional.core.model.user.ProUser
import com.hulkdx.findprofessional.core.resources.Res
import com.hulkdx.findprofessional.core.resources.aboutMe
import com.hulkdx.findprofessional.core.resources.coachType
import com.hulkdx.findprofessional.core.resources.email
import com.hulkdx.findprofessional.core.resources.firstName
import com.hulkdx.findprofessional.core.resources.lastName
import com.hulkdx.findprofessional.core.resources.price
import com.hulkdx.findprofessional.core.resources.save
import com.hulkdx.findprofessional.core.resources.skypeId
import com.hulkdx.findprofessional.core.utils.singleClick
import com.hulkdx.findprofessional.feature.pro.profile.ProProfileImage
import com.hulkdx.findprofessional.feature.pro.profile.ProProfileName
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditProProfileScreen(viewModel: EditProProfileViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    EditProProfileScreen(
        uiState = uiState,
        error = null,
        onErrorDismissed = {},
        onFirstNameChange = viewModel::onFirstNameChange,
        onLastNameChange = viewModel::onLastNameChange,
        onSkypeIdChange = viewModel::onSkypeIdChange,
        onEmailChange = viewModel::onEmailChange,
        onCoachTypeChange = viewModel::onCoachTypeChange,
        onAboutMeChange = viewModel::onAboutMeChange,
        onPriceChange = viewModel::onPriceChange,
        onSaveButtonClicked = viewModel::onSaveButtonClicked
    )
}

@Composable
fun EditProProfileScreen(
    uiState: ProUser,
    error: String?,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onSkypeIdChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCoachTypeChange: (String) -> Unit,
    onAboutMeChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onErrorDismissed: () -> Unit,
    onSaveButtonClicked: () -> Unit,
) {
    ProAppNavBarContainer(
        modifier = Modifier.testTag("EditProProfileScreen"),
        error = error,
        onErrorDismissed = onErrorDismissed,
    ) {
        EditProProfileScreenContent(
            uiState = uiState,
            onFirstNameChange = onFirstNameChange,
            onLastNameChange = onLastNameChange,
            onSkypeIdChange = onSkypeIdChange,
            onEmailChange = onEmailChange,
            onCoachTypeChange = onCoachTypeChange,
            onAboutMeChange = onAboutMeChange,
            onPriceChange = onPriceChange,
            onSaveButtonClicked = onSaveButtonClicked,
        )
    }
}

@Composable
private fun EditProProfileScreenContent(
    uiState: ProUser,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onSkypeIdChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCoachTypeChange: (String) -> Unit,
    onAboutMeChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onSaveButtonClicked: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = AppNavigationBarDimens.Height.dp)
            .navigationBarsPadding()
            .padding(horizontal = 16.dp),
        horizontalAlignment = CenterHorizontally,
    ) {
        item { ProProfileImage(uiState.profileImageUrl ?: "") }
        item { ProProfileName(uiState.fullName) }
        item {
            EditProProfileItem(
                hint = stringResource(Res.string.firstName),
                value = uiState.firstName,
                onValueChanged = onFirstNameChange,
            )
        }
        item {
            EditProProfileItem(
                hint = stringResource(Res.string.lastName),
                value = uiState.lastName,
                onValueChanged = onLastNameChange,
            )
        }
        item {
            EditProProfileItem(
                hint = stringResource(Res.string.skypeId),
                value = uiState.skypeId ?: "",
                onValueChanged = onSkypeIdChange,
            )
        }
        item {
            EditProProfileItem(
                hint = stringResource(Res.string.email),
                value = uiState.email,
                onValueChanged = onEmailChange,
            )
        }
        item {
            EditProProfileItem(
                hint = stringResource(Res.string.coachType),
                value = uiState.coachType,
                onValueChanged = onCoachTypeChange,
            )
        }
        item {
            EditProProfileItem(
                hint = stringResource(Res.string.aboutMe),
                value = uiState.description ?: "",
                onValueChanged = onAboutMeChange,
                singleLine = false,
            )
        }
        item {
            EditProProfileItem(
                hint = stringResource(Res.string.price),
                value = uiState.priceNumber?.toString() ?: "",
                onValueChanged = onPriceChange,
            )
        }
        item { SaveButton(onSaveButtonClicked) }
    }
}

@Composable
private fun EditProProfileItem(
    hint: String,
    value: String,
    onValueChanged: (String) -> Unit,
    singleLine: Boolean = true,
) {
    CUTextField(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        value = value,
        onValueChanged = onValueChanged,
        label = { Text(hint) },
        singleLine = singleLine,
    )
}

@Composable
private fun SaveButton(
    onClick: () -> Unit,
) {
    CUFilledButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        text = stringResource(Res.string.save),
        onClick = singleClick(onClick),
    )
}
