package com.example.movie_mvvm.ui.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.movie_mvvm.R
import com.example.movie_mvvm.data.api.POSTER_BASE_URL
import com.example.movie_mvvm.data.api.TheMovieDBClient
import com.example.movie_mvvm.data.repository.NetworkState
import com.example.movie_mvvm.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*


class SingleMovieActivity : AppCompatActivity() {

    private lateinit var viewModel : SingleMovieViewModel
    private lateinit var movieDetailsRepository : MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId = intent.getIntExtra("movie_id",1)
        Log.d("asfsaf",movieId.toString())


        val apiService = TheMovieDBClient.getClient()
        movieDetailsRepository = MovieDetailsRepository(apiService)
        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            Log.d("afgasdfsa",it.overview)
            bindUI(it)
        })
        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
        })
    }

    private fun  bindUI(movieDetails: MovieDetails){

        movie_title.text = movieDetails.title
        movie_tagline.text = movieDetails.tagline
        movie_overview.text = movieDetails.overview
        movie_runtime.text = movieDetails.runtime.toString()
        movie_rating.text = movieDetails.rating.toString()
        movie_release_date.text = movieDetails.releaseDate

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(movieDetails.budget)
        movie_revenue.text = formatCurrency.format(movieDetails.revenue)

        val posterUrl = POSTER_BASE_URL + movieDetails.posterPath
        Glide.with(this).load(posterUrl).into(iv_movie_poster)

    }

    private fun getViewModel(movieId : Int): SingleMovieViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieDetailsRepository, movieId) as T
            }
        }) [SingleMovieViewModel::class.java]
    }
}
