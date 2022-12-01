package com.sakine.yassirtrendingmovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.sakine.yassirtrendingmovies.R
import com.sakine.yassirtrendingmovies.repository.TrendingMoviesRepository

class TrendingMoviesActivity : AppCompatActivity() {
    lateinit var viewModel: TrendingMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val trendingMoviesRepository = TrendingMoviesRepository()
        val trendingMoviesViewModelProviderFactory = TrendingMoviesViewModelProviderFactory(application, trendingMoviesRepository)
        viewModel = ViewModelProvider(this, trendingMoviesViewModelProviderFactory)[TrendingMoviesViewModel::class.java]
    }
}