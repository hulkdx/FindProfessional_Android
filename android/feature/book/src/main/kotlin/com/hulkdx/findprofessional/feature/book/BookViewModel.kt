package com.hulkdx.findprofessional.feature.book

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hulkdx.findprofessional.common.feature.book.BookingUseCase
import com.hulkdx.findprofessional.common.feature.home.model.Professional
import com.hulkdx.findprofessional.core.utils.getStateFlow
import com.hulkdx.findprofessional.feature.book.BookNavigationScreen.Companion.ARG1
import dev.icerock.moko.resources.desc.StringDesc


class BookViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: BookingUseCase,
) : ViewModel() {
    val professional = savedStateHandle.getStateFlow<Professional>(ARG1)

    val error = savedStateHandle.getStateFlow<StringDesc?>("error", null)

    val times = useCase.getTimes()

    fun setError(error: StringDesc?) {
        savedStateHandle["error"] = error
    }

}
