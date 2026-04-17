package com.example.a7movies;

import android.os.Bundle;
import android.util.Log;

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
    private MoviesAdapter moviesAdapter;



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

        moviesAdapter = new MoviesAdapter();
        recyclerViewMovies = findViewById(R.id.moviesRecyclerView);
        recyclerViewMovies.setAdapter( moviesAdapter );
        recyclerViewMovies.setLayoutManager(                         // LayoutManager - как располагать карточки внутри recyclerView
                new GridLayoutManager( this, 2 )    // GridLayoutManager - таблицей, 2 - колонки
        );

        mainViewModel = new ViewModelProvider(this).get( MainViewModel.class );
        mainViewModel.getMovies().observe(
                this,
                movies -> {
                    Log.d("!!!!!!", movies.toString());
                    moviesAdapter.setMovies( movies );
                }
        );

        mainViewModel.loadMovies(1);

    }
}