package com.matias.kotlinrepositories.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.matias.domain.usecases.GetPagedKotlinReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    getReposUseCase: GetPagedKotlinReposUseCase,
) : ViewModel() {

    val items = getReposUseCase().cachedIn(viewModelScope)
}
