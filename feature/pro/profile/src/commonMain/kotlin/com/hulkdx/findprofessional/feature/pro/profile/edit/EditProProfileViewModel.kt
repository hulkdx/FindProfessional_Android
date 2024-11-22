package com.hulkdx.findprofessional.feature.pro.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hulkdx.findprofessional.core.model.user.ProUser
import com.hulkdx.findprofessional.feature.pro.auth.signup.usecase.GetProUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditProProfileViewModel(
    private val getProUserUseCase: GetProUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProUser())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = getProUserUseCase.getUser() ?: ProUser()
        }
    }

    fun onSaveButtonClicked() {
        // TODO:
    }

    private fun updateState(update: ProUser.() -> ProUser) {
        _uiState.update { it.update() }
    }
    fun onFirstNameChange(value: String) = updateState { copy(firstName = value) }
    fun onLastNameChange(value: String) = updateState { copy(lastName = value) }
    fun onSkypeIdChange(value: String) = updateState { copy(skypeId = value) }
    fun onEmailChange(value: String) = updateState { copy(email = value) }
    fun onCoachTypeChange(value: String) = updateState { copy(coachType = value) }
    fun onAboutMeChange(value: String) = updateState { copy(description = value) }
    fun onPriceChange(value: String) = updateState { copy(priceNumber = value.toLongOrNull()) }

}
