package com.sakine.yassirtrendingmovies.utils

import retrofit2.Response

class HandleApiResponse {

    companion object{

        fun<T> handleApiResponse(response: Response<T>): Resource<T> {

            if (response.isSuccessful){
                response.body()?.let { resultResponse ->
                    return Resource.Success(resultResponse)
                }
            }

            return Resource.Error(response.message())
        }

    }
}