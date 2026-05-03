package com.example.a7movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a7movies.database.MovieDao;
import com.example.a7movies.database.MovieDatabase;
import com.example.a7movies.models.Movie;
import com.example.a7movies.models.MovieFact;
import com.example.a7movies.models.Image;
import com.example.a7movies.models.Review;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel  extends AndroidViewModel {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<String> movieDetailsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Image>>imagesList = new MutableLiveData<>();
    private final MutableLiveData<List<Review>>reviewsList = new MutableLiveData<>();
    private final MovieDao movieDao;    // доступ к бд room. для избранных.


    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDatabase.getInstance(application).movieDao();
    }

    public  LiveData<Movie> getFavotiyrMovie(int movieId) {
        // получаем из бд запись о фильме (если есть, значит в избранных. нет - нет)
        return movieDao.getFavoriteMovie(movieId);
    }

    public LiveData<String> getMovieDetails() { return movieDetailsLiveData; }

    public LiveData<List<Image>> getImagesList() { return imagesList; }

    public LiveData<List<Review>> getReviewsList() { return reviewsList; }

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
                            Log.d("MINE", "loadMovieFacts" + throwable.toString());
                        }
                );
        compositeDisposable.add(disposable);
    }


    public void loadImages(int kinopoiskId) {
        Disposable disposable = ApiFactory.getApiService().loadImages( kinopoiskId )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .map( serverImagesResponse -> serverImagesResponse.getImagesList() )
                // в оператор .map() прилетает объект типа ServerImagesResponse. достаем из него список картинок. Это новый способ для примера как можно делать
                // можно еще так        ServerImagesResponse::getImagesList
                .subscribe(
                        serverImagesList -> {
                            // Log.d("MINE", "loadImages" + serverImagesList.toString());
                            imagesList.setValue( serverImagesList );
                        }, throwable -> {
                            Log.d("MINE", "loadImages" + throwable.toString());
                        }
                );
        compositeDisposable.add(disposable);
    }


    public void loadReviews(int kinopoiskId) {
        Disposable disposable = ApiFactory.getApiService().loadReviews( kinopoiskId )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                 .map( serverReviewsResponse -> serverReviewsResponse.getReviews() )
                // в оператор .map() прилетает объект типа ServerImagesResponse. достаем из него список картинок. Это новый способ для примера как можно делать //  можно еще так        ServerImagesResponse::getImagesList
                .subscribe(
                        serverReviewsList -> {
                            // Log.d("MINE", "loadReviews SUCCESS" + serverReviewsList.toString());
                            reviewsList.setValue( serverReviewsList );
                        }, throwable -> {
                            Log.d("MINE", "loadReviews ERROR" + throwable.toString());
                        }
                );
        compositeDisposable.add(disposable);
    }


    // >>>>>>>>>>>> favorites >>>>>>>>>>>>
    public void addToFavorites(Movie movie) {
        Disposable disposable = movieDao.addToFavorites(movie)
                                                .subscribeOn(Schedulers.io())
                                                .subscribe(
                                                        () -> {
                                                            // успешно сохранили
                                                        },
                                                        throwable -> {
                                                            // ошибка при сохранении
                                                        }
                                                );
        compositeDisposable.add(disposable);
    }

    public void removeFromFavorites(int movieId) {
        Disposable disposable = movieDao.removeFromFavorites(movieId)
                                                .subscribeOn(Schedulers.io())
                                                .subscribe(
                                                        () -> {
                                                            // успешно удалили
                                                        },
                                                        throwable -> {
                                                            // ошибка при удалении
                                                        }
                                                );
        compositeDisposable.add(disposable);
    }
    // <<<<<<<<<<<<<<< favorites <<<<<<<<<<<<<<<



    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
