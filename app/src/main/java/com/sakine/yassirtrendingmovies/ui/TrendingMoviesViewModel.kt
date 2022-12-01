package com.sakine.yassirtrendingmovies.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sakine.yassirtrendingmovies.TrendingMoviesApplication
import com.sakine.yassirtrendingmovies.models.Movie
import com.sakine.yassirtrendingmovies.models.MovieDetail
import com.sakine.yassirtrendingmovies.repository.TrendingMoviesRepository
import com.sakine.yassirtrendingmovies.utils.HandleApiResponse.Companion.handleApiResponse
import com.sakine.yassirtrendingmovies.utils.Resource
import kotlinx.coroutines.launch
import java.io.IOException

@Suppress("DEPRECATION", "UNREACHABLE_CODE")
class TrendingMoviesViewModel(
    app:Application,
    private val repository: TrendingMoviesRepository
) : AndroidViewModel(app) {

    val allTrendingMovies: MutableLiveData<Resource<Movie>> = MutableLiveData()
    val detailTrendingMovies: MutableLiveData<Resource<MovieDetail>> = MutableLiveData()

    init {
        getAllTrendingMovies()
    }

    private fun getAllTrendingMovies() = viewModelScope.launch {
        allTrendingMovies.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()){
                val response = repository.getAllTrendingMovies()
                allTrendingMovies.postValue(handleApiResponse(response))
            }else{
                allTrendingMovies.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable){
            when(t){
                is IOException -> allTrendingMovies.postValue(Resource.Error("Network Failure"))
                else -> allTrendingMovies.postValue(Resource.Error("Conversion error"))
            }
        }
    }

    fun getTrendingMoviesDetail(movie_id: String) = viewModelScope.launch {
        detailTrendingMovies.postValue(Resource.Loading())
        try {
           if (hasInternetConnection()){
               val response = repository.getTrendingMoviesDetail(movie_id)
               detailTrendingMovies.postValue(handleApiResponse(response))
           }else{
               detailTrendingMovies.postValue(Resource.Error("No internet connection"))
           }
        } catch (t: Throwable){
            when(t){
                is IOException -> detailTrendingMovies.postValue(Resource.Error("Network Failure"))
                else -> detailTrendingMovies.postValue(Resource.Error("Conversion error"))
            }
        }
    }






    private fun hasInternetConnection() : Boolean{
        val connectivityManager = getApplication<TrendingMoviesApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}