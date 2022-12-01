package com.sakine.yassirtrendingmovies.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sakine.yassirtrendingmovies.repository.TrendingMoviesRepository

@Suppress("UNCHECKED_CAST")
class TrendingMoviesViewModelProviderFactory(
    val app: Application,
    private val repository: TrendingMoviesRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TrendingMoviesViewModel(app, repository) as T
    }
}