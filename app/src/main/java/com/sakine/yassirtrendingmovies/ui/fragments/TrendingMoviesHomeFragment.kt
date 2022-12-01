package com.sakine.yassirtrendingmovies.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sakine.yassirtrendingmovies.adapters.TrendingMoviesAdapter
import com.sakine.yassirtrendingmovies.databinding.FragmentTrendingMoviesHomeBinding
import com.sakine.yassirtrendingmovies.ui.TrendingMoviesActivity
import com.sakine.yassirtrendingmovies.ui.TrendingMoviesViewModel
import com.sakine.yassirtrendingmovies.utils.Resource

class TrendingMoviesHomeFragment : Fragment() {
    private lateinit var binding: FragmentTrendingMoviesHomeBinding
    private lateinit var adapterTrendingMovies: TrendingMoviesAdapter
    private lateinit var viewModel: TrendingMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingMoviesHomeBinding.inflate(inflater, container, false)
        context ?: binding.root

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as TrendingMoviesActivity).viewModel

        setupRecyclerView()

        getAllTrendingMovies()

        adapterTrendingMovies.setOnItemClickListener {
            val direction = TrendingMoviesHomeFragmentDirections.actionTrendingMoviesHomeFragmentToTrendingMoviesDetailFragment(it)
            findNavController().navigate(direction)
        }
    }

    private fun getAllTrendingMovies(){
        viewModel.allTrendingMovies.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    response.data.let { trendingMoviesResponse ->
                        adapterTrendingMovies.differ.submitList(trendingMoviesResponse!!.results?.toList())
                        hideProgressBar()
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message->
                        Toast.makeText(activity, " An error occured: $message ", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        adapterTrendingMovies = TrendingMoviesAdapter()
        binding.rvListMovies.apply {
            adapter = adapterTrendingMovies
            layoutManager = LinearLayoutManager(activity)
        }
    }
}