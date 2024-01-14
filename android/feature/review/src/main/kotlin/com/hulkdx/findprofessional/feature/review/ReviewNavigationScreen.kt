package com.hulkdx.findprofessional.feature.review

import android.os.Bundle
import androidx.core.os.BundleCompat
import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument
import com.hulkdx.findprofessional.common.feature.home.model.Professional
import com.hulkdx.findprofessional.feature.navigation.navtype.NavTypeParcelable
import com.hulkdx.findprofessional.feature.navigation.screen.BasicNavigationScreen
import com.hulkdx.findprofessional.feature.navigation.screen.Content


class ReviewNavigationScreen : BasicNavigationScreen() {
    override val content: Content = { ReviewScreen() }

    override val route: String
        get() = "${this.javaClass.name}/{$ARG1}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(ARG1) {
                type = HomeNavType()
            }
        )

    fun destination(professional: Professional): String {
        return this.javaClass.name + "/" + HomeNavType().encodeValue(professional)
    }

    private class HomeNavType : NavTypeParcelable<Professional>(Professional::class.java)

    companion object {
        const val ARG1 = "p"

        fun professional(bundle: Bundle?): Professional {
            requireNotNull(bundle)
            return requireNotNull(BundleCompat.getParcelable(bundle, ARG1, Professional::class.java))
        }
    }
}