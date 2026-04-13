


******************************      Подключаем библиотеки

Room        - (БД)                      - гугл - Room android dependency
Glide       - (изображения)             - гугл - Glide android dependency
RxJava      - (работа с потоками)       - гугл - RxJava android dependency
Retrofit    - (работа с интернетом)     - гугл - retrofit android dependency

т.к. Исопльзуем RxJava и Retrofit => нужен адаптер, без него Retrofit не поймет, как возвращать объекты типа Observable или Single.
гугл    -   retrofit RxJava adapter     - github (Jake Wharton)    - версия устарела и ссылка на актуальную версию
        ... - по ссылке открывается адаптер для RxJava2, а у нас RxJava3    -   переходим в каталог адаптеров
        ... - выбираем rxJava3  - done





******************************      Скрываем токен от гита

 Api кинопоиска - оттуда будем брать фильмы
https://kinopoiskapiunofficial.tech/

при регистрации выдается токен. т.к. проект диблирую на гитхаб, токен нужно спрятать.
пихаем токен в local.properties (он автоматом в .gitignore)

идем в build.gradle, добавляем
1)
    import java.util.Properties

    val localProperties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

    val apiKey = localProperties.getProperty("KINOPOISKUNOFF_API_KEY") ?: ""

2)
    buildConfigField("String", "KINOPOISKUNOFF_API_KEY", "\"$apiKey\"")
в
    android {
        ...
        defaultConfig {
            ...
            buildConfigField("String", "KINOPOISKUNOFF_API_KEY", "\"$apiKey\"")
        }
3)
        buildFeatures {
            buildConfig = true
        }
в
    android {
        ...
        buildFeatures {
            buildConfig = true
        }
    }

использовать:
    String API_KEY = BuildConfig.KINOPOISKUNOFF_API_KEY;





******************************      Делаем классы для ответа сервера ( POJO )

адрес запроса
    https://kinopoiskapiunofficial.tech/api/v2.2/films
формат ответа
    {
          "total": 7,
          "totalPages": 1,
          "items": [
                {
                      "kinopoiskId": 263531,
                      "imdbId": "tt0050561",
                      "nameRu": "Мстители",
                      "nameEn": "The Avengers",
                      "nameOriginal": "The Avengers",
                      "countries": [
                            {
                              "country": "США"
                            }
                      ],
                      "genres": [
                            {
                              "genre": "фантастика"
                            }
                      ],
                      "ratingKinopoisk": 7.9,
                      "ratingImdb": 7.9,
                      "year": 2012,
                      "type": "FILM",
                      "posterUrl": "http://kinopoiskapiunofficial.tech/images/posters/kp/263531.jpg",
                      "posterUrlPreview": "https://kinopoiskapiunofficial.tech/images/posters/kp_small/301.jpg"
                }
          ]
    }
Делаем классы:
1) ServerMoviesResponse     - класс для всего ответа (поля Total, TotalPages, Items))
2) Movie                    - класс - фильм
3) Country                  - подкласс - страна
4) Genre                    - подкласс - жанр

Если поле не используется, то и делать его не надо.

Такие классы называют   POJO (Plain Old Java Object)
Это простые классы, у которых есть поля, конструкторы, геттеры и сеттеры.
Можно их делать вручную, а можно использовать спец сайт
    https://www.jsonschema2pojo.org/





******************************      ОБФУСКАЦИЯ

когда приложение выкладывается на плей маркет, оно проходит обфускацию:
Все имена классов, переменных и тд меняются на несвязные.
Делается чтоб меньше мошенников лезли.

Так вот. Получается, что в ответе приходит поле с именем kinopoiskId, а у приложения (если оно выложено на плей маркет)
вместо этого поля х пойми что.
Чтоб ретрофит понимал что в какое поле складывать, нужно у каждого поля добавлять

пример:
    @SerializedName("kinopoiskId")
    private int kinopoiskId;

это можно использовать также с другим именем:
    @SerializedName("items")
    private List<Movie> movies;
ретрофит воспринимает его как поле  "items"





******************************      Настраиваем работу с интернетом (Retrofit)

*******     1. Разрешение на использование интернета
AndroidManifest.xml     ->      <uses-permission android:name="android.permission.INTERNET" />
*******    2. создаем интерфейс ApiService.java
*******    3. создаем класс для реализации интерфейса ApiFactory.java


***********      создаем MainViewModel (паттерн view - viewmodel - model)




