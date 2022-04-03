package com.matias.kotlinrepositories.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.matias.domain.repositories.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    repo: GithubRepository,
) : ViewModel() {

    val items = repo.getKotlinRepos().cachedIn(viewModelScope)
}
