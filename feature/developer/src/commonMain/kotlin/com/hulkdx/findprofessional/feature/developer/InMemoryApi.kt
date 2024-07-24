package com.hulkdx.findprofessional.feature.developer

import com.hulkdx.findprofessional.core.model.pro.Professional
import com.hulkdx.findprofessional.feature.authentication.signup.model.RegisterRequest

interface InMemoryApi {
    fun loadKoinModules()
    fun unloadKoinModules()
    fun setUser(user: RegisterRequest)
    fun setProfessionals(pro: List<Professional>)
    fun resetProfessionals()
}
