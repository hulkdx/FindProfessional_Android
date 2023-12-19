package com.hulkdx.findprofessional.core.commonui

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hulkdx.findprofessional.core.theme.AppTheme
import com.hulkdx.findprofessional.core.theme.TextButtonColor
import com.hulkdx.findprofessional.core.theme.body2

@Composable
fun CUTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text(
            text = text,
            style = body2,
            color = TextButtonColor
        )
    }
}

@Preview
@Composable
private fun CUTextButtonPreview() {
    AppTheme {
        CUTextButton(
            Modifier,
            "Button"
        ) {}
    }
}
