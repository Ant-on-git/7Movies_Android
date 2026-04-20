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
    private int currentPage = 1;            // текущая страница для запроса
    private int responseTotalPages = 2;     // количество страниц в ответе сервера
    private boolean isLoading = false;


    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Movie>> getMovies() {  return movies; }
    // LiveData - чтоб в возвращаемый объект нельзя было загрузить новые данные (с  MutableLiveData можно)


    public void loadMovies() {
        if (isLoading || currentPage >= responseTotalPages) {
            return;
        }

        isLoading = true;   // началась загрузка ответа

        Disposable disposable = ApiFactory.getApiService().loadMovies(currentPage)     // loadMovies возвращает объект Single (RxJava)
                .subscribeOn( Schedulers.io() )                                 // обращение к серверу делаем во вспомогательном потоке Schedulers.io() (ретрофит будет делать запрос во вспомогательном потоке)
                .observeOn( AndroidSchedulers.mainThread() )                    // дальнейшую работу переключаем на главный поток (все что в .subscribe)
                .doFinally(() -> isLoading = false)                             // загрузка ответа закончилась
                .subscribe(                                                     // обрабатываем ответ
                        serverMoviesResponse -> {               // запрос удался
                            List<Movie> loaddedMovies = movies.getValue();          // получили список уже загруженных фильмов (если есть)
                            if (loaddedMovies != null) {                                    // если уже были загружены фильмы
                                loaddedMovies.addAll( serverMoviesResponse.getMovies() );   // то добавляем только что загруженные к старым
                                movies.setValue( loaddedMovies );                           // и в основной список LivaData
                            } else {                                                        // если загруженных небыло = это первая загрузка
                                movies.setValue( serverMoviesResponse.getMovies() );        // просто ставим в LiveData
                            }
                            currentPage ++;    // увеличиваем счетчик страниц

                            responseTotalPages = serverMoviesResponse.getTotalPages();      // обноыляем количество страниц в ответе сервера

                        },
                        throwable -> {                                     // ошибка
                            Log.d("*******************", "**************************************");
                            Log.d("server request error", throwable.toString());
                            Log.d("*******************", "**************************************");
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
