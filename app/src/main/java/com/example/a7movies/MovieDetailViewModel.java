package com.example.a7movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a7movies.models.MovieFact;
import com.example.a7movies.models.Image;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel  extends AndroidViewModel {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<String> movieDetailsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Image>>videosList = new MutableLiveData<>();


    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMovieDetails() {
        return movieDetailsLiveData;
    }

    public LiveData<List<Image>> getVideosList() {
        return videosList;
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
                            Log.d("MINE", throwable.toString());
                        }
                );
        compositeDisposable.add(disposable);
    }


    public void loadImages(int kinopoiskId) {
        Disposable disposable = ApiFactory.getApiService().loadImages( kinopoiskId )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        serverImagesResponse -> {
                            Log.d("MINE", serverImagesResponse.toString());
                        }, throwable -> {
                            Log.d("MINE", throwable.toString());
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
