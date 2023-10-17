package com.hulkdx.findprofessional.feature.navigation.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.hulkdx.findprofessional.core.commonui.CUSnackBar
import com.hulkdx.findprofessional.core.utils.GetStateFlowWrapper
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.StringDesc

@Composable
fun AppNavBarContainer(
    modifier: Modifier = Modifier,
    testTag: String,
    error: String?,
    onErrorDismissed: () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onTertiary)
            .systemBarsPadding()
            .testTag(testTag)
    ) {
        content()
        CUSnackBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            message = error,
            onDismiss = onErrorDismissed
        )
        AppNavigationBar()
    }
}

@Composable
fun AppNavBarContainer(
    modifier: Modifier = Modifier,
    testTag: String,
    error: GetStateFlowWrapper<StringDesc?>,
    content: @Composable () -> Unit,
) {
    val errorStr by error.collectAsStateWithLifecycle()

    AppNavBarContainer(
        modifier,
        testTag,
        errorStr?.localized(),
        { error.set(null) },
        content,
    )
}