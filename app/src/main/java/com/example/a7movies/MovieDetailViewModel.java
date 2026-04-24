package com.example.a7movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel  extends AndroidViewModel {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<String> movieDetailsLiveData = new MutableLiveData<>();


    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMovieDetails() {
        return movieDetailsLiveData;
    }

    public void loadMovieFacts(int kinopoiskId) {
        // Проверка, чтобы не грузить одно и то же при каждом повороте экрана
        if (movieDetailsLiveData.getValue() != null) return;

        Disposable disposable = ApiFactory.getApiService().loadMovieFacts(kinopoiskId)
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        serverMovieFactsResponse -> {
                            List<MovieFact> factList = serverMovieFactsResponse.getFactList();
                            StringBuilder stringBuilder = new StringBuilder();
                            // при склейке множества срок в целях оптимизации лучше использовать StringBuilder
                            for (MovieFact fact : factList) {
                                stringBuilder.append( fact.getText() ).append( "<br><br>" );
                            }

                            movieDetailsLiveData.setValue(stringBuilder.toString());

                        }, throwable -> {
                            Log.d("MINE", "**************************************");
                            Log.d("MINE", throwable.toString());
                            Log.d("MINE", "**************************************");
                        }
                );
        compositeDisposable.add(disposable);
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
