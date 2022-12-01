package com.sakine.yassirtrendingmovies.repository

import com.sakine.yassirtrendingmovies.api.RetrofitInstance

class TrendingMoviesRepository {

    suspend fun getAllTrendingMovies() = RetrofitInstance.api.getAllTrendingMovies()

    suspend fun getTrendingMoviesDetail(movie_id: String) = RetrofitInstance.api.getTrendingMoviesDetail(movie_id)
}