pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":android:app")
include(":android:core")
include(":ios")
include(":common")

include(":android:feature:authentication")
include(":android:feature:developer")
include(":android:feature:home")
include(":android:feature:navigation")
include(":android:feature:profile")
