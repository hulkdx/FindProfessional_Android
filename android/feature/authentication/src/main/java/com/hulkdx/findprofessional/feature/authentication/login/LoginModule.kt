package com.hulkdx.findprofessional.feature.authentication.login

import com.hulkdx.findprofessional.core.navigation.NavigationScreen
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val loginModule: Module
    get() = module {
        factoryOf(::LoginNavigationScreen) bind NavigationScreen::class
        viewModelOf(::LoginViewModel)
    }

