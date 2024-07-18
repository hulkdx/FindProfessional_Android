package com.hulkdx.findprofessional.core.config

import android.content.Context
import kotlin.system.exitProcess

class PlatformSpecificAndroid(
    private val appContext: Context,
) : PlatformSpecific {

    override fun isDebug() = com.hulkdx.findprofessional.core.config.isDebug()
    
    override fun localhostUrl() = ""

    override fun appDirectoryPath(): String {
        return appContext.filesDir.absolutePath
    }
}

actual fun isDebug(): Boolean {
    return false
}

actual fun closeApp() {
    exitProcess(0)
}
