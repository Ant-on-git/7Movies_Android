


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



******  Api кинопоиска - оттуда будем брать фильмы
https://kinopoiskapiunofficial.tech/
