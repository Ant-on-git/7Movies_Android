package com.example.a7movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritesActivity extends AppCompatActivity {
    private FavoritesViewModel favoritesViewModel;
    private RecyclerView recyclerViewFav;
    private MainMoviesAdapter mainMoviesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorites);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        favoritesViewModel.getFavoritesList().observe(
                this,
                favMovList -> mainMoviesAdapter.setMovies( favMovList )
        );

        mainMoviesAdapter = new MainMoviesAdapter();

        recyclerViewFav = findViewById(R.id.favoritesRecyclerView);
        recyclerViewFav.setAdapter( mainMoviesAdapter );
        recyclerViewFav.setLayoutManager(                         // LayoutManager - как располагать карточки внутри recyclerView
                new GridLayoutManager( this, 2 )    // GridLayoutManager - таблицей, 2 - колонки
        );

        mainMoviesAdapter.setOnMovieClickListener(
                movie ->  {
                    Intent intent = MovieDetailActivity.newIntent(FavoritesActivity.this, movie);
                    startActivity(intent);
                }
        );
    }


    public  static Intent newIntent (Context context) {
        Intent intent = new Intent(context, FavoritesActivity.class);
        return intent;
    }
}