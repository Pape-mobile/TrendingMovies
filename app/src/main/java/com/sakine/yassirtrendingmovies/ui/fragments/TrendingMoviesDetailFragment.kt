package com.sakine.yassirtrendingmovies.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.sakine.yassirtrendingmovies.R
import com.sakine.yassirtrendingmovies.databinding.FragmentTrendingMoviesDetailBinding
import com.sakine.yassirtrendingmovies.ui.TrendingMoviesActivity
import com.sakine.yassirtrendingmovies.ui.TrendingMoviesViewModel
import com.sakine.yassirtrendingmovies.utils.Constants.Companion.IMAGE_URL
import com.sakine.yassirtrendingmovies.utils.Resource

class TrendingMoviesDetailFragment : Fragment(R.layout.fragment_trending_movies_detail) {
    private var _binding: FragmentTrendingMoviesDetailBinding? = null
    private val binding get() = _binding!!
    private val args: TrendingMoviesDetailFragmentArgs by navArgs()
    private lateinit var viewModel: TrendingMoviesViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentTrendingMoviesDetailBinding.bind(view)

        viewModel = (activity as TrendingMoviesActivity).viewModel

        getTrendingMoviesDetail()

        binding.backToHome.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getTrendingMoviesDetail() {
        viewModel.getTrendingMoviesDetail(args.movieDetail.id.toString())
        viewModel.detailTrendingMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    response.data.let { detailMoviesResponse ->
                        hideProgressBar()
                        Glide.with(requireActivity())
                            .load(IMAGE_URL + detailMoviesResponse?.poster_path)
                            .into(binding.posterPath)
                        binding.originalTitle.text = detailMoviesResponse?.original_title
                        binding.releaseDate.text = detailMoviesResponse?.release_date
                        binding.voteAverage.text = detailMoviesResponse?.vote_average.toString()
                        binding.originalDescription.text = detailMoviesResponse?.overview

                        binding.chipGroup.removeAllViews()
                        for (genre in detailMoviesResponse?.genres!!) {
                            addChip(genre.name!!)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, " An error occured: $message ", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun addChip(genre: String) {
        val chip = Chip(requireActivity())
        chip.text = genre
        binding.chipGroup.addView(chip)
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
}