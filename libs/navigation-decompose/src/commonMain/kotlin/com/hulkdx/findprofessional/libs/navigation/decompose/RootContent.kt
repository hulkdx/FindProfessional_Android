package com.hulkdx.findprofessional.libs.navigation.decompose

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.hulkdx.findprofessional.core.navigation.NavigationScreen
import org.koin.compose.koinInject

@Composable
fun RootContent(
    component: RootComponent,
    screenContent: @Composable (NavigationScreen) -> Unit,
) {
    Children(
        stack = component.stack,
        animation = stackAnimation(slide()),
    ) {
        ChildrenContent(it.instance, screenContent)
    }
}

@Composable
private fun ChildrenContent(
    child: NavigationScreen,
    screenContent: @Composable (NavigationScreen) -> Unit,
) {
    SetupCurrentScreenNavigator(child)
    screenContent(child)
}

@Composable
private fun SetupCurrentScreenNavigator(
    child: NavigationScreen,
    navigator: DecomposeNavigator = koinInject(),
) {
    navigator._currentScreen = child
}
