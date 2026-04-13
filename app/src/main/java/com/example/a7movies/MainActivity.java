package com.example.a7movies;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

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

        ApiFactory.getApiService().loadMovies()
                                .subscribeOn( Schedulers.io() )
                                .subscribeOn(AndroidSchedulers.mainThread() )
                                .subscribe(
                                        serverMoviesResponse -> {
                                            Log.d("!!!!!!!!!!!!!!!!!!!", "!!!!!!!!!!!!!!!!!!!");
                                            Log.d("API_KEY", serverMoviesResponse.toString());
                                            Log.d("!!!!!!!!!!!!!!!!!!!", "!!!!!!!!!!!!!!!!!!!");
                                        }, throwable -> {
                                            Log.d("*******************", "*******************");
                                            Log.d("API_KEY", throwable.toString());
                                            Log.d("*******************", "*******************");
                                        }
                                );



    }
}