package com.hulkdx.findprofessional.feature.book.time.utils

import com.hulkdx.findprofessional.core.navigation.NavigationScreen
import com.hulkdx.findprofessional.core.navigation.Navigator

class StubNavigator : Navigator {
    override fun navigate(screen: NavigationScreen) {}

    override fun navigate(
        screen: NavigationScreen,
        popTo: NavigationScreen,
        inclusive: Boolean,
    ) {
    }

    override fun goBack() {}

    override fun getCurrentScreen(): NavigationScreen {
        throw RuntimeException()
    }
}
