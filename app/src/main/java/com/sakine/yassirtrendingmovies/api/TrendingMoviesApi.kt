package com.sakine.yassirtrendingmovies.api

import com.sakine.yassirtrendingmovies.models.Movie
import com.sakine.yassirtrendingmovies.models.MovieDetail
import com.sakine.yassirtrendingmovies.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrendingMoviesApi {

    @GET("discover/movie")
    suspend fun getAllTrendingMovies(
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<Movie>

    @GET("movie/{movie_id}")
    suspend fun getTrendingMoviesDetail(
        @Path("movie_id") movie_id: String,
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<MovieDetail>
}