package com.example.movie_mvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movie_mvvm.R
import com.example.movie_mvvm.ui.single_movie_details.SingleMovieActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener{
            val intent = Intent(this, SingleMovieActivity::class.java)
            intent.putExtra("movie_id",299534)
            startActivity(intent)
        }
    }
}
