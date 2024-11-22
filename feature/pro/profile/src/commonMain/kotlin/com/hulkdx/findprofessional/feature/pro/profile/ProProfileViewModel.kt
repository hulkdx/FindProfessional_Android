package com.hulkdx.findprofessional.feature.pro.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hulkdx.findprofessional.core.navigation.Navigator
import com.hulkdx.findprofessional.core.utils.StringOrRes
import com.hulkdx.findprofessional.feature.authentication.login.usecase.LogoutUseCase
import com.hulkdx.findprofessional.feature.pro.auth.signup.usecase.GetProUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProProfileViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val navigator: Navigator,
    getProUserUseCase: GetProUserUseCase,
) : ViewModel() {
    val uiState = getProUserUseCase.getUserFlow()
        .filterNotNull()
        .map {
            UiState(
                name = "${it.firstName} ${it.lastName}",
                profileImageUrl = it.profileImageUrl.toString(),
            )
        }
        .stateIn(viewModelScope, WhileSubscribed(5_000), UiState())

    private val _error = MutableStateFlow<StringOrRes?>(null)
    val error = _error.asStateFlow()

    fun onEditProfileClicked() {
        // TODO: Navigate to edit profile screen
        // navigator.navigate()
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            logoutUseCase.logout()
        }
    }

    fun onErrorDismissed() {
        _error.update { null }
    }

    data class UiState(
        val name: String = "",
        val profileImageUrl: String = "",
    )
}
