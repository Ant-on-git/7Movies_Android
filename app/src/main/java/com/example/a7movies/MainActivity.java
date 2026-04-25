package com.example.a7movies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private RecyclerView recyclerViewMovies;
    private MainMoviesAdapter mainMoviesAdapter;
    private ProgressBar progressbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        mainViewModel = new ViewModelProvider(this).get( MainViewModel.class );
        mainViewModel.getMovies().observe(
                this,
                movies -> mainMoviesAdapter.setMovies( movies )
        );


        mainMoviesAdapter = new MainMoviesAdapter();
        recyclerViewMovies = findViewById( R.id.moviesRecyclerView );
        recyclerViewMovies.setAdapter( mainMoviesAdapter );
        recyclerViewMovies.setLayoutManager(                         // LayoutManager - как располагать карточки внутри recyclerView
                new GridLayoutManager( this, 2 )    // GridLayoutManager - таблицей, 2 - колонки
        );

        // устанавливаем в адаптер колбэк - дозагрузка фильмов при прокрутке до конца (вызывается в onBindViewHolder)
        mainMoviesAdapter.setOnMoviesListEndListener( () ->  mainViewModel.loadMovies() );
        // колбэк - вызов экрана с детальной информацией о фильме
        mainMoviesAdapter.setOnMovieClickListener(
                movie ->  {
                    Intent intent = MovieDetailActivity.newIntent(MainActivity.this, movie);
                    startActivity(intent);
                }
        );



        progressbar = findViewById(R.id.loadingProgressBar);
        // отображение прогресс бара
        mainViewModel.getIsLoading().observe(
                this,
                isLoading -> {
                    if ( isLoading ) { progressbar.setVisibility( View.VISIBLE ); }
                    else             { progressbar.setVisibility( View.GONE ); }
                }
        );



    }
}