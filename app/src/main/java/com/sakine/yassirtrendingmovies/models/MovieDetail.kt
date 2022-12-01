package com.sakine.yassirtrendingmovies.models

data class MovieDetail(
    var adult: Boolean?,
    var backdrop_path: String?,
    var belongs_to_collection: Any?,
    var budget: Int?,
    var genres: List<Genre>?,
    var homepage: String?,
    var id: Int?,
    var imdb_id: String?,
    var original_language: String?,
    var original_title: String?,
    var overview: String?,
    var popularity: Double?,
    var poster_path: String?,
    var production_companies: List<ProductionCompany>?,
    var production_countries: List<ProductionCountry>?,
    var release_date: String?,
    var revenue: Int?,
    var runtime: Int?,
    var spoken_languages: List<SpokenLanguage>?,
    var status: String?,
    var tagline: String?,
    var title: String?,
    var video: Boolean?,
    var vote_average: Double?,
    var vote_count: Int?
)