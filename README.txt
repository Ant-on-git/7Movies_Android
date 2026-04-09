


********* Подключаем библиотеки


1.

Room        - (БД)                      - гугл - Room android dependency
Glide       - (изображения)             - гугл - Glide android dependency
RxJava      - (работа с потоками)       - гугл - RxJava android dependency
Retrofit    - (работа с интернетом)     - гугл - retrofit android dependency

т.к. Исопльзуем RxJava и Retrofit => нужен адаптер, без него Retrofit не поймет, как возвращать объекты типа Observable или Single.
гугл    -   retrofit RxJava adapter     - github (Jake Wharton)    - версия устарела и ссылка на актуальную версию
        ... - по ссылке открывается адаптер для RxJava2, а у нас RxJava3    -   переходим в каталог адаптеров
        ... - выбираем rxJava3  - done



 Api кинопоиска - оттуда будем брать фильмы
https://kinopoiskapiunofficial.tech/


***************** Скрываем токен от гита
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
