package com.example.a7movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();        // список фильмов
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();  // "список" объектов для закрытия процессов RxJava


    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Movie>> getMovies() {  return movies; }
    // LiveData - чтоб в возвращаемый объект нельзя было загрузить новые данные (с  MutableLiveData можно)


    public void loadMovies(int page) {
        Disposable disposable = ApiFactory.getApiService().loadMovies(page)     // loadMovies возвращает объект Single (RxJava)
                .subscribeOn( Schedulers.io() )                                 // обращение к серверу делаем во вспомогательном потоке Schedulers.io() (ретрофит будет делать запрос во вспомогательном потоке)
                .observeOn( AndroidSchedulers.mainThread() )                    // дальнейшую работу переключаем на главный поток (все что в .subscribe)
                .subscribe(                                                     // обрабатываем ответ
                        serverMoviesResponse -> {               // запрос удался
                            movies.setValue( serverMoviesResponse.getMovies() );
                        },
                        throwable -> {                                     // ошибка
                            Log.d("*******************", "*******************");
                            Log.d("server request error", throwable.toString());
                            Log.d("*******************", "*******************");
                        }
                );
        compositeDisposable.add(disposable);    // добавляем объект для закрытия процесса RxJava в "список" таких объектов
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();  // закрываем процессы RxJava при закрытии экрана \ класса
    }
}
