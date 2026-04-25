package com.example.a7movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.a7movies.models.Movie;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView movieDetail_poster;
    private TextView movieDetail_title;
    private TextView movieDetail_year;
    private TextView movieDetail_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        initViews();

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        // .. = getIntent().getSerializableExtra("movie");          - получим объект типа serializable
        // .. = (Movie) getIntent().getSerializableExtra("movie");  - переводим объект serializable в тип Movie

        Glide.with(this).load(movie.getPosterUrl()).into(movieDetail_poster);
        movieDetail_title.setText( movie.getNameRu() );
        movieDetail_year.setText( String.valueOf( movie.getYear() ) );


        // получаем детальную инф о фильме
        MovieDetailViewModel movieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        movieDetailViewModel.getMovieDetails().observe(
                this,
                movieFacts -> {
                    movieDetail_description.setText( Html.fromHtml( movieFacts, Html.FROM_HTML_MODE_COMPACT ) );
                }
        );
        movieDetailViewModel.loadMovieFacts( movie.getKinopoiskId() );
        movieDetailViewModel.loadImages( movie.getKinopoiskId() );

    }


    private void initViews() {
        movieDetail_poster      = findViewById(R.id.movieDetail_poster);
        movieDetail_title       = findViewById(R.id.movieDetail_title);
        movieDetail_year        = findViewById(R.id.movieDetail_year);
        movieDetail_description = findViewById(R.id.movieDetail_description);
    }



    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("movie", movie);
        return intent;
    }
}