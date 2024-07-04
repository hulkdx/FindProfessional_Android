package com.hulkdx.findprofessional.app.di

import com.hulkdx.findprofessional.common.apiModule
import com.hulkdx.findprofessional.common.config.PlatformSpecific
import com.hulkdx.findprofessional.common.config.storage.datastore.datastoreModule
import com.hulkdx.findprofessional.common.feature.authentication.login.loginModule
import com.hulkdx.findprofessional.common.feature.authentication.logout.logoutModule
import com.hulkdx.findprofessional.common.feature.authentication.signup.signUpModule
import com.hulkdx.findprofessional.common.feature.book.bookModule
import com.hulkdx.findprofessional.common.feature.home.homeModule
import com.hulkdx.findprofessional.common.feature.review.reviewModule
import com.hulkdx.findprofessional.common.navigation.NavigationScreen
import com.hulkdx.findprofessional.common.navigation.Navigator
import com.hulkdx.findprofessional.common.setupApiInterceptors
import com.hulkdx.findprofessional.feature.authentication.splash.splashModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(
            testModule,
            apiModule,
            datastoreModule,
            loginModule,
            signUpModule,
            logoutModule,
            homeModule,
            reviewModule,
            bookModule,
            splashModule,
        )
    }

    setupApiInterceptors()
}

val testModule: Module
    get() = module {
        factory {
            object : Navigator {
                override fun navigate(screen: NavigationScreen) {
                }

                override fun navigate(
                    screen: NavigationScreen,
                    popTo: NavigationScreen,
                    inclusive: Boolean,
                ) {
                }

                override fun goBack() {
                }

                override fun getCurrentScreen(): NavigationScreen {
                    TODO()
                }
            }
        } bind Navigator::class

        factory {
            object : PlatformSpecific {
                override fun isDebug(): Boolean {
                    return true
                }

                override fun localhostUrl(): String {
                    return "10.0.2.2"
                }

                override fun appDirectoryPath(): String {
                    return ""
                }
            }
        } bind PlatformSpecific::class
    }